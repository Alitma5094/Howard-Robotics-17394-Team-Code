package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.hardware.DcMotor
import kotlin.math.sqrt

@Autonomous
class FirstAuto : LinearOpMode() {
    val robot = Robot(this)

    override fun runOpMode() {
        robot.init()

//        val target =
//            5.0 / (Robot.WHEEL_CIRCUMFERENCE * sqrt(2.0)) * Robot.MOVEMENT_MOTOR_TICK_COUNT

//        val dpt = (3.78 * Math.PI) / 537.7 // Distance per tick
//        val target = 5.0 / dpt

//        telemetry.addData("Target", target)
        telemetry.addData("Ticks per rev", robot.motors[Motors.LF]?.motorType?.ticksPerRev)
        telemetry.addData("Status", "Initialized")
        telemetry.update()
        waitForStart()
        robot.moveByDistance(12.0, 0.3)

//        robot.setMotorsMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER, Motors.LF, Motors.LB, Motors.RF, Motors.RB)
//
//        robot.setMotorsTargetPos(-target, Motors.LF, Motors.RF, Motors.LB, Motors.RB)
//        robot.setMotorsPower(-0.2, Motors.RF, Motors.RB)
//        robot.setMotorsPower(0.2, Motors.LF, Motors.LB)
//        robot.setMotorsMode(DcMotor.RunMode.RUN_TO_POSITION, Motors.LF, Motors.RF, Motors.LB, Motors.RB)
//
//        while (robot.areAllMotorsBusy(Motors.LF, Motors.RF, Motors.LB, Motors.RB)) {
//        }
//
//        robot.setMotorsPower(0.0, Motors.LF, Motors.RF, Motors.LB, Motors.RB)
//        robot.setMotorsMode(DcMotor.RunMode.RUN_USING_ENCODER, Motors.LF, Motors.RF, Motors.LB, Motors.RB)

        telemetry.addData("Done", "true")
        telemetry.update()
    }
}

fun Double.toInches(): Double {
    return this / 2.54
}

fun Double.toCentimeters(): Double {
    return this * 2.54
}