package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class FirstOpMode extends LinearOpMode {
    @Override
    public void runOpMode() {
        DcMotor topLeftWheel = hardwareMap.get(DcMotor.class, "topLeft");
        DcMotor topRightWheel = hardwareMap.get(DcMotor.class, "topRight");
        DcMotor bottomLeftWheel = hardwareMap.get(DcMotor.class, "bottomLeft");
        DcMotor bottomRightWheel = hardwareMap.get(DcMotor.class, "bottomRight");

        Servo armWrist = hardwareMap.get(Servo.class, "wrist");
        double wristTgtPos = armWrist.getPosition();


        waitForStart();

        while (opModeIsActive()) {
            //Forwards and Backwards movements
            topLeftWheel.setPower(gamepad1.left_stick_y);
            topRightWheel.setPower(-gamepad1.left_stick_y);
            bottomLeftWheel.setPower(gamepad1.left_stick_y);
            bottomRightWheel.setPower(-gamepad1.left_stick_y);

            //Right Movement
            if(gamepad1.left_stick_x < 0){
                topLeftWheel.setPower(gamepad1.left_stick_x);
                bottomLeftWheel.setPower(-gamepad1.left_stick_x);
                topRightWheel.setPower(gamepad1.left_stick_x);
                bottomRightWheel.setPower(-gamepad1.left_stick_x);
            }
            //Left Movement
            if(gamepad1.left_stick_x > 0){
                topLeftWheel.setPower(gamepad1.left_stick_x);
                bottomLeftWheel.setPower(-gamepad1.left_stick_x);
                topRightWheel.setPower(gamepad1.left_stick_x);
                bottomRightWheel.setPower(-gamepad1.left_stick_x);
            }

            //Rotation
            if(gamepad1.left_trigger > 0){
                topLeftWheel.setPower(gamepad1.left_stick_y);
                topRightWheel.setPower(gamepad1.left_stick_y);
                bottomLeftWheel.setPower(gamepad1.left_stick_y);
                bottomRightWheel.setPower(gamepad1.left_stick_y);
            }
            if(gamepad1.right_trigger > 0){
                topLeftWheel.setPower(-gamepad1.left_stick_y);
                topRightWheel.setPower(-gamepad1.left_stick_y);
                bottomLeftWheel.setPower(-gamepad1.left_stick_y);
                bottomRightWheel.setPower(-gamepad1.left_stick_y);
            }

            wristTgtPos = wristTgtPos  + -this.gamepad2.left_stick_y;
            armWrist.setPosition(wristTgtPos);
        }
    }
}