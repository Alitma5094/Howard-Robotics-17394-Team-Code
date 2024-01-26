package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class FirstOpMode extends LinearOpMode {
    DcMotor topLeftWheel = hardwareMap.get(DcMotor.class, "topLeft");
    DcMotor topRightWheel = hardwareMap.get(DcMotor.class, "topRight");
    DcMotor bottomLeftWheel = hardwareMap.get(DcMotor.class, "bottomLeft");
    DcMotor bottomRightWheel = hardwareMap.get(DcMotor.class, "bottomRight");
    DcMotor armBase = hardwareMap.get(DcMotor.class, "armBase");
    DcMotor armExt = hardwareMap.get(DcMotor.class, "armExt");
    @Override
    public void runOpMode() {
        Servo armWrist = hardwareMap.get(Servo.class, "wrist");
        double wristTgtPos = armWrist.getPosition();

        //creating a variable to get current position of Arm motors
        //and set how powerfully they move
        int armBasePos = armBase.getCurrentPosition();
        armBase.setTargetPosition(armBasePos);
        armBase.setPower(0.5);
        armBase.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        int armExtPos = armExt.getCurrentPosition();
        armExt.setTargetPosition(armExtPos);
        armExt.setPower(0.5);
        armExt.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        waitForStart();

        while (opModeIsActive()) {
            //Forwards and Backwards movements
            topLeftWheel.setPower(gamepad1.left_stick_y);
            topRightWheel.setPower(-gamepad1.left_stick_y);
            bottomLeftWheel.setPower(gamepad1.left_stick_y);
            bottomRightWheel.setPower(-gamepad1.left_stick_y);
            //Right Movement

            do {
                topLeftWheel.setPower(-gamepad1.left_stick_x);
                bottomLeftWheel.setPower(gamepad1.left_stick_x);
                topRightWheel.setPower(-gamepad1.left_stick_x);
                bottomRightWheel.setPower(gamepad1.left_stick_x);
            }
            while(gamepad1.left_stick_x > 0);

            //Left Movement
            do {
                topLeftWheel.setPower(-gamepad1.left_stick_x);
                bottomLeftWheel.setPower(gamepad1.left_stick_x);
                topRightWheel.setPower(-gamepad1.left_stick_x);
                bottomRightWheel.setPower(gamepad1.left_stick_x);
            }
            while(gamepad1.left_stick_x < 0);
            //Rotation
            if(gamepad1.left_trigger > 0){
                topLeftWheel.setPower(gamepad1.left_trigger);
                topRightWheel.setPower(gamepad1.left_trigger);
                bottomLeftWheel.setPower(gamepad1.left_trigger);
                bottomRightWheel.setPower(gamepad1.left_trigger);
            }
            if(gamepad1.right_trigger > 0){
                topLeftWheel.setPower(-gamepad1.right_trigger);
                topRightWheel.setPower(-gamepad1.right_trigger);
                bottomLeftWheel.setPower(-gamepad1.right_trigger);
                bottomRightWheel.setPower(-gamepad1.right_trigger);
            }

// Method to set motor powers with smoothing


//
//            //Base Arm movement in both directions
//            if(gamepad2.right_stick_y < 0){
//                armBasePos += 5;
//            }
//            if(gamepad2.right_stick_y > 0){
//                armBasePos -= 5;
//            }
//            else{
//                armBasePos = 0;
//            }
//            armBase.setTargetPosition(armBasePos);
//            armBase.setPower(-gamepad2.right_stick_y/2);
//
//            //Elbow Arm movement in both directions
//            if(gamepad2.left_stick_y < 0){
//                armExtPos += 5;
//            }
//            if(gamepad2.left_stick_y > 0){
//                armExtPos -= 5;
//            }
//            else{
//                armExtPos = 0;
//            }
//            armExt.setTargetPosition(armExtPos);
//            armExt.setPower(gamepad2.left_stick_y/2);
            double basePowerBottom = -gamepad2.right_stick_y / 2;
            if (basePowerBottom != 0) {
                armBasePos += 5 * Math.signum(basePowerBottom); // Gradual increase/decrease
            } else {
                armBasePos = 0;
            }
            armBase.setTargetPosition(armBasePos);
            armBase.setPower(basePowerBottom);

            double basePowerTop = -gamepad2.right_stick_y / 2;
            if (basePowerTop != 0) {
                armExtPos += 5 * Math.signum(basePowerTop); // Gradual increase/decrease
            } else {
                armExtPos = 0;
            }
            armExt.setTargetPosition(armExtPos);
            armExt.setPower(basePowerTop);
        }
    }
}