package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp

@TeleOp
class FirstTeleOp : LinearOpMode() {
    private val robot = Robot(this)

    override fun runOpMode() {
        robot.init()
        telemetry.addData("Status", "Initialized")
        telemetry.update()
        waitForStart()

        while (opModeIsActive()) {

            // Front - Back Movement
            robot.setMotorsPower(gamepad1.left_stick_y.toDouble(), Motors.TopLeft)
            robot.setMotorsPower(gamepad1.right_stick_y.toDouble(), Motors.TopRight)
            robot.setMotorsPower(gamepad1.left_stick_y.toDouble(), Motors.BottomLeft)
            robot.setMotorsPower(gamepad1.right_stick_y.toDouble(), Motors.BottomRight)

            // Right Movement
            if (gamepad1.right_trigger > 0) {
                robot.setMotorsPower(-gamepad1.right_trigger.toDouble(), Motors.TopLeft)
                robot.setMotorsPower(gamepad1.right_trigger.toDouble(), Motors.TopRight)
                robot.setMotorsPower(gamepad1.right_trigger.toDouble(), Motors.BottomLeft)
                robot.setMotorsPower(-gamepad1.right_trigger.toDouble(), Motors.BottomRight)
            }

            // Left Movement
            if (gamepad1.left_trigger > 0) {
                robot.setMotorsPower(gamepad1.left_trigger.toDouble(), Motors.TopLeft)
                robot.setMotorsPower(-gamepad1.left_trigger.toDouble(), Motors.TopRight)
                robot.setMotorsPower(-gamepad1.left_trigger.toDouble(), Motors.BottomLeft)
                robot.setMotorsPower(gamepad1.left_trigger.toDouble(), Motors.BottomRight)
            }
        }
    }
}