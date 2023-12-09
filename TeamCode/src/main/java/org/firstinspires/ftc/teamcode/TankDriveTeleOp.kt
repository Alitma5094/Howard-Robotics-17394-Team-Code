package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.commands.AnalogCommands.GetMotorCurrentCommand

@TeleOp
class TankDriveTeleOp : LinearOpMode() {
    private val robot = Robot(this)

    override fun runOpMode() {
        robot.init()

        telemetry.addData("Status", robot.getMotorPos(Motors.ArmLeft).toDouble())
        telemetry.update()

        waitForStart()


        var motorPosR = robot.getMotorPos(Motors.ArmRight).toDouble()
        robot.setMotorsTargetPos(motorPosR, Motors.ArmRight)
        robot.setMotorsPower(0.3, Motors.ArmRight)
        robot.setMotorsMode(DcMotor.RunMode.RUN_TO_POSITION, Motors.ArmRight)

        var motorPosL = robot.getMotorPos(Motors.ArmLeft).toDouble()
        robot.setMotorsTargetPos(motorPosL, Motors.ArmLeft)
        robot.setMotorsPower(0.03, Motors.ArmLeft)
        robot.setMotorsMode(DcMotor.RunMode.RUN_TO_POSITION, Motors.ArmLeft)


        while (opModeIsActive()) {
            robot.setMotorsPower(gamepad1.left_stick_y.toDouble(), Motors.TopLeft)
            robot.setMotorsPower(gamepad1.left_stick_y.toDouble(), Motors.BottomLeft)
            robot.setMotorsPower(gamepad1.right_stick_y.toDouble(), Motors.TopRight)
            robot.setMotorsPower(gamepad1.right_stick_y.toDouble(), Motors.BottomRight)

            telemetry.addData("Status", robot.getMotorPos(Motors.ArmLeft).toDouble())
            telemetry.update()

            if (gamepad2.right_stick_y.toDouble() < 0) {
                motorPosR += 5
            }

            if (gamepad2.right_stick_y.toDouble() > 0) {
                motorPosR -= 5
            }


            robot.setMotorsTargetPos(motorPosR, Motors.ArmRight)


            if (gamepad2.left_stick_y.toDouble() < 0) {
                motorPosL += 5
            }

            if (gamepad2.left_stick_y.toDouble() > 0) {
                motorPosL -= 5
            }
//
//
            robot.setMotorsTargetPos(motorPosL, Motors.ArmLeft)

//            robot.setMotorsPower(gamepad2.left_stick_y.toDouble()/2, Motors.ArmLeft)
//            robot.setMotorsPower(-gamepad2.right_stick_y.toDouble()/2, Motors.ArmRight)


            // Right Movement
            if (gamepad1.right_trigger > 0) {
                robot.setMotorsPower(-gamepad1.right_trigger.toDouble(), Motors.TopLeft)
                robot.setMotorsPower(gamepad1.right_trigger.toDouble(), Motors.BottomLeft)
                robot.setMotorsPower(gamepad1.right_trigger.toDouble(), Motors.TopRight)
                robot.setMotorsPower(-gamepad1.right_trigger.toDouble(), Motors.BottomRight)
            }

             // Left Movement
            if (gamepad1.left_trigger > 0) {
                robot.setMotorsPower(gamepad1.left_trigger.toDouble(), Motors.TopLeft)
                robot.setMotorsPower(-gamepad1.left_trigger.toDouble(), Motors.BottomLeft)
                robot.setMotorsPower(-gamepad1.left_trigger.toDouble(), Motors.TopRight)
                robot.setMotorsPower(gamepad1.left_trigger.toDouble(), Motors.BottomRight)
            }

        }
    }
}