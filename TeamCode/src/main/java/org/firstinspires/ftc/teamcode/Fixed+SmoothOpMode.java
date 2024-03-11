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
    public void runOpMode() throws InterruptedException{
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
double rX=0, rY=0,newX,newY;
        waitForStart();

        while (opModeIsActive()) {
            //translational movement
            Thread.sleep(5);//wait 5ms
            
             newX=0.95*(rX)+0.05*(gamepad1.right_stick_x);//5% smoothing every 5ms maybe try 1% later idk 
             newY=0.95*(rY)+0.05*(gamepad1.right_stick_y);
            
            topLeftWheel.setPower((-newX+newY)/2);//averages the vector componets and does x and y plane motion
            topRightWheel.setPower((newX-newY)/2);
            bottomLeftWheel.setPower((-newX+newY)/2);
            bottomRightWheel.setPower((newX-newY)/2);
            
            rX=newX;// updates old values for future smoothing
            rY=newY;

            
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
