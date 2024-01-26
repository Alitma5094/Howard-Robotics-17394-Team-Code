package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

public class Robot {

    public enum Motors {
        TopLeft,
        TopRight,
        BottomLeft,
        BottomRight,
        ArmLeft,
        ArmRight
    }

    private OpMode opMode;
    private HashMap<Motors, DcMotor> motors;

    public Robot(OpMode opMode) {
        this.opMode = opMode;
    }

    private HardwareMap getHardwareMap() {
        return opMode.hardwareMap;
    }

    public void init() {
        motors = new HashMap<>();
        motors.put(Motors.TopLeft, getHardwareMap().get(DcMotor.class, "topLeft"));
        motors.put(Motors.TopRight, getHardwareMap().get(DcMotor.class, "topRight"));
        motors.put(Motors.BottomLeft, getHardwareMap().get(DcMotor.class, "bottomLeft"));
        motors.put(Motors.BottomRight, getHardwareMap().get(DcMotor.class, "bottomRight"));
        motors.put(Motors.ArmLeft, getHardwareMap().get(DcMotor.class, "armLeft"));
        motors.put(Motors.ArmRight, getHardwareMap().get(DcMotor.class, "armRight"));

        setMotorDirections();
    }

    private void setMotorDirections() {
        motors.get(Motors.TopLeft).setDirection(DcMotorSimple.Direction.FORWARD);
        motors.get(Motors.TopRight).setDirection(DcMotorSimple.Direction.REVERSE);
        motors.get(Motors.BottomLeft).setDirection(DcMotorSimple.Direction.FORWARD);
        motors.get(Motors.BottomRight).setDirection(DcMotorSimple.Direction.REVERSE);
        motors.get(Motors.ArmLeft).setDirection(DcMotorSimple.Direction.FORWARD);
        motors.get(Motors.ArmRight).setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public void setMotorsMode(DcMotor.RunMode mode, Motors... motors) {
        for (Motors motor : motors) {
            this.motors.get(motor).setMode(mode);
        }
    }

    public int getMotorPos(Motors motor) {
        return this.motors.get(motor).getCurrentPosition();
    }

    public void setMotorsPower(double power, Motors... motors) {
        for (Motors motor : motors) {
            this.motors.get(motor).setPower(power);
        }
    }

    public void setMotorsTargetPos(double position, Motors... motors) {
        for (Motors motor : motors) {
            this.motors.get(motor).setTargetPosition((int) position);
        }
    }

    private boolean isMotorBusy(Motors motor) {
        return motors.get(motor).getMode() == DcMotor.RunMode.RUN_TO_POSITION && motors.get(motor).isBusy();
    }

    private boolean areAllMotorsBusy(Motors... motors) {
        for (Motors motor : motors) {
            if (!isMotorBusy(motor)) {
                return false;
            }
        }
        return true;
    }

    public void moveByDistance(double inches, double power) {
        final double DEFAULT_MOTOR_POWER = 0.5;
        final double MOTOR_TICK_COUNT = 537.7;
        final double WHEEL_DIAMETER = 3.78; // Inches
        final double WHEEL_CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER; // Inches

        double target = inches / (WHEEL_CIRCUMFERENCE / MOTOR_TICK_COUNT);

        setMotorsMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER, Motors.TopLeft, Motors.BottomLeft, Motors.TopRight, Motors.BottomRight);

        setMotorsTargetPos(-target, Motors.TopLeft, Motors.TopRight, Motors.BottomLeft, Motors.BottomRight);
        setMotorsPower(-power, Motors.TopRight, Motors.BottomRight);
        setMotorsPower(power, Motors.TopLeft, Motors.BottomLeft);
        setMotorsMode(DcMotor.RunMode.RUN_TO_POSITION, Motors.TopLeft, Motors.TopRight, Motors.BottomLeft, Motors.BottomRight);

        while (areAllMotorsBusy(Motors.TopLeft, Motors.TopRight, Motors.BottomLeft, Motors.BottomRight)) {
            // Wait for motors to reach target position
        }

        setMotorsPower(0.0, Motors.TopLeft, Motors.TopRight, Motors.BottomLeft, Motors.BottomRight);
        setMotorsMode(DcMotor.RunMode.RUN_USING_ENCODER, Motors.TopLeft, Motors.TopRight, Motors.BottomLeft, Motors.BottomRight);
    }

    public void rotateBySeconds(double seconds, double power,  Consumer<Long> customSleep, String direction) {
        // Assuming a standard tank drive with left and right motors
        final double DEFAULT_MOTOR_POWER = 0.5;

        setMotorsMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER, Motors.TopLeft, Motors.BottomLeft, Motors.TopRight, Motors.BottomRight);

        // Set power for turning
        if (Objects.equals(direction, "left")) {
            setMotorsPower(-power, Motors.TopLeft, Motors.BottomLeft);
            setMotorsPower(power, Motors.TopRight, Motors.BottomRight);
        } else {
            setMotorsPower(power, Motors.TopLeft, Motors.BottomLeft);
            setMotorsPower(-power, Motors.TopRight, Motors.BottomRight);
        }

        // Use the custom sleep function to wait for the specified duration
        customSleep.accept((long) (seconds * 1000));

        // Stop the motors
        setMotorsPower(0.0, Motors.TopLeft, Motors.BottomLeft, Motors.TopRight, Motors.BottomRight);

        // Set back to default mode
        setMotorsMode(DcMotor.RunMode.RUN_USING_ENCODER, Motors.TopLeft, Motors.BottomLeft, Motors.TopRight, Motors.BottomRight);
    }


}
