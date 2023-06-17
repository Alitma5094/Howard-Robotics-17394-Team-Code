package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor

@TeleOp
class testObj : LinearOpMode() {
    private val robot = Robot(this)

    override fun runOpMode() {
        robot.init()
        telemetry.addData("Status", "Initialized")
//        telemetry.addData("Status", robot.motors[Motors.LF]?.motorType?.ticksPerRev)
        telemetry.update()
        waitForStart()

        while (opModeIsActive()) {

            // Front - Back
            robot.setMotorPower(Motors.LF, gamepad1.left_stick_y.toDouble())
            robot.setMotorPower(Motors.RF, gamepad1.right_stick_y.toDouble())
            robot.setMotorPower(Motors.LB, gamepad1.left_stick_y.toDouble())
            robot.setMotorPower(Motors.RB, gamepad1.right_stick_y.toDouble())

            // Right
            if (gamepad1.right_trigger > 0) {
                robot.setMotorPower(Motors.LF, -gamepad1.right_trigger.toDouble())
                robot.setMotorPower(Motors.RF, gamepad1.right_trigger.toDouble())
                robot.setMotorPower(Motors.LB, gamepad1.right_trigger.toDouble())
                robot.setMotorPower(Motors.RB, -gamepad1.right_trigger.toDouble())
            }

            // Left
            if (gamepad1.left_trigger > 0) {
                robot.setMotorPower(Motors.LF, gamepad1.left_trigger.toDouble())
                robot.setMotorPower(Motors.RF, -gamepad1.left_trigger.toDouble())
                robot.setMotorPower(Motors.LB, -gamepad1.left_trigger.toDouble())
                robot.setMotorPower(Motors.RB, gamepad1.left_trigger.toDouble())
            }

            if (gamepad1.a) {
                telemetry.addData("A", "Pressed")
                telemetry.update()
                val target = 5 / 537.7 * (3.78 * Math.PI)
                robot.setMotorsMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER, Motors.LF, Motors.LB, Motors.RF, Motors.RB)

                robot.setMotorsTargetPos(-target, Motors.LF, Motors.RF, Motors.LB, Motors.RB)
                robot.setMotorsPower(-0.2, Motors.RF, Motors.RB)
                robot.setMotorsPower(0.2, Motors.LF, Motors.LB)
                robot.setMotorsMode(DcMotor.RunMode.RUN_TO_POSITION, Motors.LF, Motors.RF, Motors.LB, Motors.RB)

                while (robot.areAllMotorsBusy(Motors.LF, Motors.RF, Motors.LB, Motors.RB)) {
                    telemetry.addData("while", "true")
                    telemetry.update()
                }

                robot.setMotorsPower(0.0, Motors.LF, Motors.RF, Motors.LB, Motors.RB)
                robot.setMotorsMode(DcMotor.RunMode.RUN_USING_ENCODER, Motors.LF, Motors.RF, Motors.LB, Motors.RB)
                telemetry.addData("Done", "true")
                telemetry.update()
            }
        }
    }
}