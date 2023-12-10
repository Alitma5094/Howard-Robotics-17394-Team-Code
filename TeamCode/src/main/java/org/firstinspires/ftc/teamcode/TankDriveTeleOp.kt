package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor

@TeleOp
class TankDriveTeleOp : LinearOpMode() {
    private val robot = Robot(this)

    override fun runOpMode() {
        robot.init()

        telemetry.addData("Status", robot.getMotorPos(Motors.ArmLeft).toDouble())
        telemetry.update()

        waitForStart()
        //Keep arms in their places
        //ArmRight = Extension
        //ArmLeft = Base
        var motorPosR = robot.getMotorPos(Motors.ArmRight).toDouble()
        robot.setMotorsTargetPos(motorPosR, Motors.ArmRight)
        robot.setMotorsPower(0.5, Motors.ArmRight)
        robot.setMotorsMode(DcMotor.RunMode.RUN_TO_POSITION, Motors.ArmRight)

        var motorPosL = robot.getMotorPos(Motors.ArmLeft).toDouble()
/*        robot.setMotorsTargetPos(motorPosL, Motors.ArmLeft)
        robot.setMotorsPower(0.0005, Motors.ArmLeft)
        robot.setMotorsMode(DcMotor.RunMode.RUN_TO_POSITION, Motors.ArmLeft)
*/

        while (opModeIsActive()) {
            telemetry.addData("Status", robot.getMotorPos(Motors.ArmLeft).toDouble())
            telemetry.update()
            //Actuate Arm Extension in both directions
            if (gamepad2.right_stick_y.toDouble() < 0) {
                motorPosR += 5
            }
            if (gamepad2.right_stick_y.toDouble() > 0) {
                motorPosR -= 5
            }
            robot.setMotorsTargetPos(motorPosR, Motors.ArmRight)

            //Actuate Arm Base in both directions
            if (gamepad2.left_stick_y.toDouble() < 0) {
                motorPosL += 5
            }
            if (gamepad2.left_stick_y.toDouble() > 0) {
                motorPosL -= 5
            }
            robot.setMotorsTargetPos(motorPosL, Motors.ArmLeft)

//            robot.setMotorsPower(gamepad2.left_stick_y.toDouble()/2, Motors.ArmLeft)
//            robot.setMotorsPower(-gamepad2.right_stick_y.toDouble()/2, Motors.ArmRight)

            //Forward/backward Movement
            robot.setMotorsPower(gamepad1.left_stick_y.toDouble(), Motors.TopLeft)
            robot.setMotorsPower(gamepad1.left_stick_y.toDouble(), Motors.BottomLeft)
            robot.setMotorsPower(gamepad1.left_stick_y.toDouble(), Motors.TopRight)
            robot.setMotorsPower(gamepad1.left_stick_y.toDouble(), Motors.BottomRight)

            // Right Movement
            if (gamepad1.left_stick_x > 0) {
                robot.setMotorsPower(-gamepad1.left_stick_x.toDouble(), Motors.TopLeft)
                robot.setMotorsPower(gamepad1.left_stick_x.toDouble(), Motors.BottomLeft)
                robot.setMotorsPower(gamepad1.left_stick_x.toDouble(), Motors.TopRight)
                robot.setMotorsPower(-gamepad1.left_stick_x.toDouble(), Motors.BottomRight)
            }
             // Left Movement
            if (gamepad1.left_stick_x < 0) {
                robot.setMotorsPower(gamepad1.left_stick_x.toDouble(), Motors.TopLeft)
                robot.setMotorsPower(-gamepad1.left_stick_x.toDouble(), Motors.BottomLeft)
                robot.setMotorsPower(-gamepad1.left_stick_x.toDouble(), Motors.TopRight)
                robot.setMotorsPower(gamepad1.left_stick_x.toDouble(), Motors.BottomRight)
            }
            //Rotation
            if(gamepad1.left_trigger > 0){
                robot.setMotorsPower(gamepad1.left_trigger.toDouble(), Motors.TopLeft)
                robot.setMotorsPower(gamepad1.left_trigger.toDouble(), Motors.BottomLeft)
                robot.setMotorsPower(-gamepad1.left_trigger.toDouble(), Motors.TopRight)
                robot.setMotorsPower(-gamepad1.left_trigger.toDouble(), Motors.BottomRight)
            }
            if(gamepad1.right_trigger > 0){
                robot.setMotorsPower(-gamepad1.right_trigger.toDouble(), Motors.TopLeft)
                robot.setMotorsPower(-gamepad1.right_trigger.toDouble(), Motors.BottomLeft)
                robot.setMotorsPower(gamepad1.right_trigger.toDouble(), Motors.TopRight)
                robot.setMotorsPower(gamepad1.right_trigger.toDouble(), Motors.BottomRight)
            }
            if(gamepad2.x == true){
                robot.setServoPoition(1.0, Servos.Claw)
            }
            if(gamepad2.b == true){
                robot.setServoPoition(0.0, Servos.Claw)
            }
            if(gamepad2.left_trigger > 0){
                robot.setServoPoition(gamepad2.left_trigger.toDouble(), Servos.Wrist)
            }
            if(gamepad2.right_trigger > 0){
                robot.setServoPoition(gamepad2.right_trigger.toDouble(), Servos.Wrist)
            }
        }
    }
}