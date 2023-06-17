package org.firstinspires.ftc.teamcode



import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.*
import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry


enum class Motors {
    LF,
    RF,
    LB,
    RB,
}

class Robot(val opMode: OpMode) {
    lateinit var motors: HashMap<Motors, DcMotor>

    private val hardwareMap: HardwareMap
        get() = opMode.hardwareMap

//    val opModeIsActive
//        get() = (opMode as LinearOpMode).opModeIsActive()
//
//    lateinit var angles: Orientation
//    lateinit var gravity: Acceleration
//
//    var lastAngles: Orientation = Orientation()
//    var globalAngle: Double = 0.0

    fun init() {
        motors = hashMapOf(
            Motors.LF to hardwareMap.get(DcMotor::class.java, "topLeft"),
            Motors.RF to hardwareMap.get(DcMotor::class.java, "topRight"),
            Motors.LB to hardwareMap.get(DcMotor::class.java, "bottomLeft"),
            Motors.RB to hardwareMap.get(DcMotor::class.java, "bottomRight"),
        )

        motors[Motors.LF]?.direction = DcMotorSimple.Direction.FORWARD
        motors[Motors.RF]?.direction = DcMotorSimple.Direction.REVERSE
        motors[Motors.LB]?.direction = DcMotorSimple.Direction.FORWARD
        motors[Motors.RB]?.direction = DcMotorSimple.Direction.REVERSE
    }



    fun setMotorMode(motor: Motors, mode: DcMotor.RunMode) {
        this.motors[motor]?.mode = mode
    }

    fun setMotorsMode(mode: DcMotor.RunMode, vararg motors: Motors) {
        for (motor in motors) {
            setMotorMode(motor, mode)
        }
    }

    fun setMotorPower(motor: Motors, power: Double) {
        this.motors[motor]?.power = power
    }

    fun setMotorsPower(power: Double, vararg motors: Motors) {
        for (motor in motors) {
            setMotorPower(motor, power)
        }
    }

    fun setMotorTargetPos(motor: Motors, position: Double) {
        this.motors[motor]?.targetPosition = position.toInt()
    }

    fun setMotorsTargetPos(position: Double, vararg motors: Motors) {
        for (motor in motors) {
            setMotorTargetPos(motor, position)
        }
    }

    fun isMotorBusy(motor: Motors): Boolean {
        return motors[motor]?.mode == DcMotor.RunMode.RUN_TO_POSITION && motors[motor]?.isBusy!!
    }

    fun areAllMotorsBusy(vararg motors: Motors): Boolean {
        return motors.all { m -> isMotorBusy(m) }
    }

    fun areMotorsBusy(vararg motors: Motors): Boolean {
        return motors.any { m -> isMotorBusy(m) }
    }

    fun moveByDistance(inches: Double, power: Double = Robot.DEFAULT_MOTOR_POWER) {
        val dpt = (3.78 * Math.PI) / 537.7 // Distance per tick
        val target = inches / dpt

        setMotorsMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER, Motors.LF, Motors.LB, Motors.RF, Motors.RB)

        setMotorsTargetPos(-target, Motors.LF, Motors.RF, Motors.LB, Motors.RB)
        setMotorsPower(-power, Motors.RF, Motors.RB)
        setMotorsPower(power, Motors.LF, Motors.LB)
        setMotorsMode(DcMotor.RunMode.RUN_TO_POSITION, Motors.LF, Motors.RF, Motors.LB, Motors.RB)

        while (areAllMotorsBusy(Motors.LF, Motors.RF, Motors.LB, Motors.RB)) {
        }

        setMotorsPower(0.0, Motors.LF, Motors.RF, Motors.LB, Motors.RB)
        setMotorsMode(DcMotor.RunMode.RUN_USING_ENCODER, Motors.LF, Motors.RF, Motors.LB, Motors.RB)
    }

    companion object {
        const val DEFAULT_MOTOR_POWER = 0.5
        const val MOVEMENT_MOTOR_TICK_COUNT = 2786 * 19.2
        const val WHEEL_DIAMETER = 3.78 // Inches
        const val RATIO = 3.0
        const val WHEEL_CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER // Inches
    }
}