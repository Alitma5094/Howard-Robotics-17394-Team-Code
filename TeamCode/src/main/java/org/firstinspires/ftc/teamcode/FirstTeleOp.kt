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

        robot.moveByDistance(inches = 22.0, power = 0.5)
        for (i in 1..3) {
            robot.setMotorsPower(0.3, Motors.TopLeft)
            robot.setMotorsPower(-0.3, Motors.TopRight)
            robot.setMotorsPower(0.3, Motors.BottomLeft)
            robot.setMotorsPower(-0.3, Motors.BottomRight)
            Thread.sleep(3000)
            robot.setMotorsPower(
                0.0,
                Motors.TopLeft,
                Motors.TopRight,
                Motors.BottomLeft,
                Motors.BottomRight,
            )
            //check if cone there bruv
            Thread.sleep(3000)
        }
    }
}