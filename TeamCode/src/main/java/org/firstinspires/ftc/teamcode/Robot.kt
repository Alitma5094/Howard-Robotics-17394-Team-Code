package org.firstinspires.ftc.teamcode
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.*


enum class Motors {
    TopLeft,
    TopRight,
    BottomLeft,
    BottomRight,
}

enum class DiagonalDirection {
    ForwardLeft,
    ForwardRight,
    BackwardLeft,
    BackwardRight,
}

class Robot(private val opMode: OpMode) {
    lateinit var motors: HashMap<Motors, DcMotor>

    private val hardwareMap: HardwareMap
        get() = opMode.hardwareMap

    fun init() {
        motors = hashMapOf(
            Motors.TopLeft to hardwareMap.get(DcMotor::class.java, "topLeft"),
            Motors.TopRight to hardwareMap.get(DcMotor::class.java, "topRight"),
            Motors.BottomLeft to hardwareMap.get(DcMotor::class.java, "bottomLeft"),
            Motors.BottomRight to hardwareMap.get(DcMotor::class.java, "bottomRight"),
        )

        motors[Motors.TopLeft]?.direction = DcMotorSimple.Direction.FORWARD
        motors[Motors.TopRight]?.direction = DcMotorSimple.Direction.REVERSE
        motors[Motors.BottomLeft]?.direction = DcMotorSimple.Direction.FORWARD
        motors[Motors.BottomRight]?.direction = DcMotorSimple.Direction.REVERSE
    }

    private fun setMotorsMode(mode: DcMotor.RunMode, vararg motors: Motors) {
        for (motor in motors) {
            this.motors[motor]?.mode = mode
        }
    }

    fun setMotorsPower(power: Double, vararg motors: Motors) {
        for (motor in motors) {
            this.motors[motor]?.power = power
        }
    }

    private fun setMotorsTargetPos(position: Double, vararg motors: Motors) {
        for (motor in motors) {
            this.motors[motor]?.targetPosition = position.toInt()
        }
    }

    private fun isMotorBusy(motor: Motors): Boolean {
        return motors[motor]?.mode == DcMotor.RunMode.RUN_TO_POSITION && motors[motor]?.isBusy!!
    }

    private fun areAllMotorsBusy(vararg motors: Motors): Boolean {
        return motors.all { m -> isMotorBusy(m) }
    }

    fun moveByDistance(inches: Double, power: Double = DEFAULT_MOTOR_POWER) {
        val target = inches / (WHEEL_CIRCUMFERENCE / MOTOR_TICK_COUNT)

        setMotorsMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER, Motors.TopLeft, Motors.BottomLeft, Motors.TopRight, Motors.BottomRight)

        setMotorsTargetPos(-target, Motors.TopLeft, Motors.TopRight, Motors.BottomLeft, Motors.BottomRight)
        setMotorsPower(-power, Motors.TopRight, Motors.BottomRight)
        setMotorsPower(power, Motors.TopLeft, Motors.BottomLeft)
        setMotorsMode(DcMotor.RunMode.RUN_TO_POSITION, Motors.TopLeft, Motors.TopRight, Motors.BottomLeft, Motors.BottomRight)

        while (areAllMotorsBusy(Motors.TopLeft, Motors.TopRight, Motors.BottomLeft, Motors.BottomRight)) { }

        setMotorsPower(0.0, Motors.TopLeft, Motors.TopRight, Motors.BottomLeft, Motors.BottomRight)
        setMotorsMode(DcMotor.RunMode.RUN_USING_ENCODER, Motors.TopLeft, Motors.TopRight, Motors.BottomLeft, Motors.BottomRight)
    }

    fun turnByDegrees(degrees: Double, power: Double = DEFAULT_MOTOR_POWER){
        throw NotImplementedError(message = "This is not implemented yet.")
    }
    fun moveDiagonalByTime(time: Double, direction: DiagonalDirection, power: Double = DEFAULT_MOTOR_POWER){
        when (direction) {
            DiagonalDirection.ForwardLeft -> {
                throw NotImplementedError(message = "This is not implemented yet.")
            }
            DiagonalDirection.ForwardRight -> {
                throw NotImplementedError(message = "This is not implemented yet.")
            }
            DiagonalDirection.BackwardLeft -> {
                throw NotImplementedError(message = "This is not implemented yet.")
            }
            else -> {
                throw NotImplementedError(message = "This is not implemented yet.")
            }
        }
    }

    companion object {
        const val DEFAULT_MOTOR_POWER = 0.5
        const val MOTOR_TICK_COUNT = 537.7
        const val WHEEL_DIAMETER = 3.78 // Inches
        const val WHEEL_CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER // Inches
    }
}