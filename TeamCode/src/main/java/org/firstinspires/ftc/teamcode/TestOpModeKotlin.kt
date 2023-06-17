package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor

@TeleOp
class TestOpModeKotlin : LinearOpMode() {
    //Chassis
    private var topLeft: DcMotor? = null
    private var topRight: DcMotor? = null
    private var bottomLeft: DcMotor? = null
    private var bottomRight: DcMotor? = null

    //Make sure method is correct
    override fun runOpMode() {

        // expansion hub mapping chassis
        topLeft = hardwareMap.get(DcMotor::class.java, "topLeft")
        topRight = hardwareMap.get(DcMotor::class.java, "topRight")
        bottomLeft = hardwareMap.get(DcMotor::class.java, "bottomLeft")
        bottomRight = hardwareMap.get(DcMotor::class.java, "bottomRight")

        // elementary initialization
        telemetry.addData("Status", "Initialized")
        telemetry.update()

        // true when driver clicks start
        waitForStart()
        while (opModeIsActive()) {

            //##################################################
            // Chassis
            //##################################################


            // checks if pressure greater than 0, returns double corresponding to pressure on trigger (value between 0 and 1)
            if (gamepad1.right_trigger > 0) {
                topLeft?.power = -gamepad1.right_trigger.toDouble()
                topRight?.power = gamepad1.right_trigger.toDouble()
                bottomLeft?.power = -gamepad1.right_trigger.toDouble()
                bottomRight?.power = gamepad1.right_trigger.toDouble()
            }
            if (gamepad1.left_trigger > 0) {
                topLeft?.power = -gamepad1.left_trigger.toDouble()
                topRight?.power = gamepad1.left_trigger.toDouble()
                bottomLeft?.power = -gamepad1.left_trigger.toDouble()
                bottomRight?.power = gamepad1.left_trigger.toDouble()
            }

            //DIAGONAL MOVEMENT (D-PAD) controls (unfinished?)
            //Make sure to check which numbers are supposed to be negative or positive.
            if (gamepad1.dpad_up) { //diagonal TR
                topLeft?.power = 1.0
                bottomRight?.power = 1.0
                bottomLeft?.power = 0.0
                topRight?.power = 0.0
            }
            if (gamepad1.dpad_right) { //diagonal BR
                bottomLeft?.power = 1.0
                topRight?.power = 1.0
                topLeft?.power = 0.0
                bottomRight?.power = 0.0
            }
            if (gamepad1.dpad_down) { //diagonal BL
                topLeft?.power = -1.0
                bottomRight?.power = -1.0
                bottomLeft?.power = 0.0
                topRight?.power = 0.0
            }
            if (gamepad1.dpad_left) { //diagonal TL
                bottomLeft?.power = -1.0
                topRight?.power = -1.0
                topLeft?.power = 0.0
                bottomRight?.power = 0.0
            }

            topLeft?.power = gamepad1.left_stick_y.toDouble() //chassis top left
            topRight?.power = -gamepad1.right_stick_y.toDouble() // chassis bottom left
            bottomLeft?.power = gamepad1.left_stick_y.toDouble() // chassis top right
            bottomRight?.power = -gamepad1.right_stick_y.toDouble() // chassis bottom right

            topLeft?.power = gamepad1.left_stick_x.toDouble()
            topRight?.power = -gamepad1.left_stick_x.toDouble()
            bottomLeft?.power = -gamepad1.left_stick_x.toDouble()
            bottomRight?.power = gamepad1.left_stick_x.toDouble()



            //telemetry update the variable values
            telemetry.addData("Status", "Running")
            telemetry.addData("dcMotorTL", topLeft?.power)
            telemetry.addData("dcMotorBL", topRight?.power)
            telemetry.addData("dcMotorTR", bottomLeft?.power)
            telemetry.addData("dcMotorBR", bottomRight?.power)
            telemetry.update()
        }
    }
}