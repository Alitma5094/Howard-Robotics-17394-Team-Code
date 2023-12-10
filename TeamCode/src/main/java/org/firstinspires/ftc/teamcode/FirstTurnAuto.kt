package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode

@Autonomous
class FirstTurnAuto : LinearOpMode() { //this is importante dont delet
    private val robot = Robot(this)

    override fun runOpMode() {
        robot.init()

        while (opModeIsActive()) {

            // Front - Back Movement
            robot.setMotorsPower(0.1, Motors.TopLeft)
            robot.setMotorsPower(0.1, Motors.TopRight)
            robot.setMotorsPower(0.1, Motors.BottomLeft)
            robot.setMotorsPower(0.1, Motors.BottomRight)
            Thread.sleep(1000)

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