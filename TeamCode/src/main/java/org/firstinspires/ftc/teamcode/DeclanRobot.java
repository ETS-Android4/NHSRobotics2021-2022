package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Util;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.Duck;
import org.firstinspires.ftc.teamcode.subsystems.Arm;




@TeleOp(name = "Declan Teleop", group = "TeleOp")
public class DeclanRobot extends LinearOpMode {
    //Intake
    DcMotor intakeMotor;
    Intake intakewheel;

    //Drive
    SampleMecanumDrive mecanumDrive;

    //Arm
    DcMotorEx armMotor;
    Arm arm;

    //Bucket Servo
    CRServo clawServo;
    Claw claw;
    
    //Duck Spin
    DcMotor duckMotor;
    Duck duckSpin;



    //Boolean
    private Boolean d_loop = false;
    private boolean reverse = false;
    private boolean precise = false;

    @Override
    public void runOpMode() throws InterruptedException {

        //Drive
        mecanumDrive = new SampleMecanumDrive(hardwareMap);

        //Intake
        intakeMotor = hardwareMap.dcMotor.get("intake");
        intakewheel = new Intake(intakeMotor);

        //Bucket Servo
        clawServo = hardwareMap.crservo.get("claw");
        claw = new Claw(clawServo);

        //Arm
        armMotor = hardwareMap.get(DcMotorEx.class, "arm");
        arm = new Arm(armMotor);

        //Duck Spin
        duckMotor = hardwareMap.dcMotor.get("duck");
        duckSpin = new Duck(duckMotor);


        


        waitForStart();
        //armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        while (opModeIsActive()) {

            //Drive
            if (gamepad1.dpad_down) {
                reverse = true;
            }

            if (gamepad1.dpad_up) {
                precise = true;
            }

            // Precise driving activated with Dpad_up
            // To exit press Dpad_up again
            while (precise && opModeIsActive()) {
                telemetry.addData("Mode", "PRECISE");
                telemetry.update();

                mecanumDrive.setDrivePower(
                        new Pose2d((gamepad1.left_stick_y)/2.5,
                                (gamepad1.right_stick_x)/2.5,
                                (gamepad1.left_stick_x)/2.5));
                mecanumDrive.updatePoseEstimate();

                if (gamepad1.dpad_right) {
                    telemetry.addData("Mode", "NORMAL");
                    precise = false;
                }

            }

            // Reverse driving activated with Dpad_down
            // To exit press Dpad_down again
            while (reverse && opModeIsActive()) {
                telemetry.addData("Mode", "REVERSE");
                telemetry.update();

                mecanumDrive.setDrivePower(
                        new Pose2d(-gamepad1.left_stick_y,
                                -gamepad1.right_stick_x,
                                -gamepad1.left_stick_x));
                mecanumDrive.updatePoseEstimate();

                if (gamepad1.dpad_right) {
                    telemetry.addData("Mode", "NORMAL");
                    reverse = false;
                }

            }


            // Normal Driving
            mecanumDrive.setDrivePower(
                    new Pose2d(gamepad1.left_stick_y,
                            gamepad1.right_stick_x,
                            gamepad1.left_stick_x));
            mecanumDrive.updatePoseEstimate();

            telemetry.update();


            //Intake motor
            if (gamepad2.left_trigger >= 0.5) {
                intakewheel.In();
            } else {
                intakewheel.Off();
            }

            if (gamepad2.right_trigger >= 0.5) {
                intakewheel.Out();
            } else {
                intakewheel.Off();

            }



            
            // Arm Motors
            if (gamepad2.a && !armMotor.isBusy()) {
                telemetry.addData("Level:", "1" );
                arm.Level1();
            }

            if (gamepad2.b && !armMotor.isBusy()) {
                telemetry.addData("Level:", "2" );
                arm.Level2();
            }

            if (gamepad2.y && !armMotor.isBusy()) {
                telemetry.addData("Level:", "3" );
                arm.Level3();
            }

            if (gamepad2.x && !armMotor.isBusy()) {
                telemetry.addData("Level:", "Home" );
                arm.Home();
            }
            




            // Claw
            if (gamepad2.dpad_down) {
                claw.Open();
            }

            if (gamepad2.dpad_up) {
                claw.Close();
            }






            //DUCK SPIN
            if (gamepad2.left_bumper) {
                duckSpin.Spin();
            } else{
                duckSpin.DontSpin();
            }

            if (gamepad2.right_bumper) {
                duckSpin.ReverseSpin();
            } else{
                duckSpin.DontSpin();
            }




            telemetry.update();

        }
        idle();
    }
}

