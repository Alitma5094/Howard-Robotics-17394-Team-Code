package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode

@Autonomous
class FirstAuto : LinearOpMode() {
    private val robot = Robot(this)

    override fun runOpMode() {
        robot.init()

        telemetry.addData("Ticks per rev", robot.motors[Motors.TopLeft]?.motorType?.ticksPerRev)
        telemetry.addData("Status", "Initialized")
        telemetry.update()

        waitForStart()

        robot.moveByDistance(12.0, 0.3)

        telemetry.addData("Done", "true")
        telemetry.update()
    }
}