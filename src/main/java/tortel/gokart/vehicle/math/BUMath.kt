package tortel.gokart.vehicle.math

import kotlin.math.*

class BUMath {
    companion object {

        //Chapter 3: Forces
        /**
         * 3.1 Aerodynamic drag
         *
         * Calculates the aerodynamic drag force acting on a car in motion.
         *
         * When the car is in motion, an aerodynamic drag will develop that will resist
         * the motion of the car. Drag force is proportional to the square of the velocity.
         *
         * The formula to calculate the force is as follows:
         *
         * `F_drag = -C_drag * V * |V|`
         *
         * where:
         * - `V` is the velocity vector of the car.
         * - `C_drag` is a constant, which is proportional to the frontal area of the car.
         *
         * @param cDrag The drag coefficient, which is proportional to the frontal area of the car.
         * @param velocity The velocity vector of the car.
         *
         * @return The aerodynamic drag force acting on the car.
         * */
        fun fDrag(cDrag: Double, velocity: Double): Double {
            return -cDrag * velocity * abs(velocity)
        }


        /**
         * 3.2 Rolling resistance
         *
         * Calculates the rolling resistance force acting on a car.
         *
         * Rolling resistance is caused by friction between the rubber and road surfaces
         * as the wheels roll along and is proportional to the velocity.
         *
         * The formula to calculate the force is as follows:
         *
         * `F_rr = -C_rr * V`
         *
         * where:
         * - `V` is the velocity vector of the car.
         * - `C_rr` is a constant.
         *
         * @param cRr The rolling resistance coefficient, a constant representing the frictional properties between the tire and the road surface.
         * @param velocity The velocity vector of the car in meters per second (m/s).
         * @return The rolling resistance force acting on the car in Newtons (N).
         */
        fun fRR(cRr: Double, velocity: Double): Double {
            return -cRr * velocity
        }

        /**
         * 3.3 Gravity
         *
         * Calculates the gravitational force component acting on a car on an incline.
         *
         * Gravity pulls the car towards the earth. The parallel component of the gravitational force can pull the car
         * either forwards or backwards depending on whether the car is pointing uphill or downhill.
         *
         * The formula to calculate the force is as follows:
         *
         * `F_g = m * g * sin(θ)`
         *
         * where:
         * - `m` is the mass of the car.
         * - `g` is the acceleration due to gravity (default is 9.81 m/s²).
         * - `θ` is the angle of the incline in radians.
         *
         * @param mass The mass of the car in kilograms (kg).
         * @param angle The angle of the incline in radians (rad).
         * @param gravity The acceleration due to gravity, default is 9.81 m/s².
         * @return The gravitational force component acting on the car in Newtons (N).
         */
        fun fG(mass: Double, angle: Double, gravity: Double = 9.81): Double {
            return mass * gravity * sin(angle)
        }

        /**
         * 3.4 Traction
         *
         * Calculates the traction force acting on the car's wheels.
         *
         * The engine generates torque, which when applied to the wheels causes them
         * to rotate. Friction between the tires and the ground resists this motion,
         * resulting in a force applied to the tires in the direction opposite to the
         * rotation of the tires.
         *
         * The formula to calculate the force is as follows:
         *
         * `F_traction = T_wheel / R_wheel`
         *
         * where:
         * - `T_wheel` is the torque applied to the wheels.
         * - `R_wheel` is the radius of the wheel.
         *
         * @param tWheel The torque applied to the wheels in Newton-meters (Nm).
         * @param rWheel The radius of the wheel in meters (m).
         * @return The traction force applied to the wheels in Newtons (N).
         */
        fun fTraction(tWheel: Double, rWheel: Double): Double {
            return tWheel / rWheel
        }

        /**
         * 3.5 Total longitudinal force
         *
         * Calculates the total longitudinal force acting on the car.
         *
         * To determine the position of the car, first we must find the net force on the car.
         * The total longitudinal force is the vector sum of the traction force, drag force,
         * rolling resistance force, and gravitational force.
         *
         * The formula to calculate the force is as follows:
         *
         * `F_long = F_traction + F_drag + F_rr + F_g`
         *
         * where:
         * - `F_traction` is the traction force.
         * - `F_drag` is the aerodynamic drag force.
         * - `F_rr` is the rolling resistance force.
         * - `F_g` is the gravitational force component.
         *
         * @param fTraction The traction force acting on the car in Newtons (N).
         * @param fDrag The aerodynamic drag force acting on the car in Newtons (N).
         * @param fRR The rolling resistance force acting on the car in Newtons (N).
         * @param fG The gravitational force component acting on the car in Newtons (N).
         * @return The total longitudinal force acting on the car in Newtons (N).
         */
        fun fLong(fTraction: Double, fDrag: Double, fRR: Double, fG: Double): Double {
            return fTraction + fDrag + fRR + fG
        }

        /**
         * 3.5 Braking force
         *
         * Calculates the braking force acting on the car.
         *
         * When braking, a braking force replaces the traction force, which is oriented
         * in the opposite direction. A simple model of braking is as follows:
         *
         * The formula to calculate the force is as follows:
         *
         * `F_braking = -u * C_braking`
         *
         * where:
         * - `u` is a unit vector in the direction of the car’s heading.
         * - `C_braking` is a constant.
         *
         * @param u The unit vector in the direction of the car's heading.
         * @param cBraking The braking coefficient, a constant representing the braking force per unit vector.
         * @return The braking force acting on the car in Newtons (N).
         */
        fun fBraking(u: Double, cBraking: Double): Double {
            return -u * cBraking
        }

        /**
         * 3.5 Acceleration
         *
         * Calculates the acceleration of the car based on the total longitudinal force and the mass of the car.
         *
         * The acceleration of the car is determined by the net force on the car and the car's mass via Newton's second law.
         *
         * The formula to calculate the acceleration is as follows:
         *
         * `a = F_long / m`
         *
         * where:
         * - `F_long` is the total longitudinal force acting on the car.
         * - `m` is the mass of the car.
         *
         * @param f The total longitudinal force acting on the car in Newtons (N).
         * @param m The mass of the car in kilograms (kg).
         * @return The acceleration of the car in meters per second squared (m/s²).
         */
        fun acceleration(f: Double, m: Double): Double {
            return f / m
        }

        /**
         * 3.5 Updating the velocity of the car
         *
         * Updates the velocity of the car based on the current velocity, time increment, and acceleration.
         *
         * The car's velocity is determined by integrating the acceleration over time using a numerical method.
         *
         * The formula to update the velocity is as follows:
         *
         * `V_new = V + Δt * a`
         *
         * where:
         * - `V` is the current velocity.
         * - `Δt` is the time increment.
         * - `a` is the acceleration.
         *
         * @param v The current velocity of the car in meters per second (m/s).
         * @param dt The time increment in seconds (s).
         * @param a The acceleration of the car in meters per second squared (m/s²).
         * @return The updated velocity of the car in meters per second (m/s).
         */
        fun vNew(v: Double, dt: Double, a: Double): Double {
            return v + dt * a
        }

        /**
         * 3.5 Updating the position of the car
         *
         * Updates the position of the car based on the current position, time increment, and velocity.
         *
         * The car's position is determined by integrating the velocity over time using a numerical method.
         *
         * The formula to update the position is as follows:
         *
         * `P_new = P + Δt * V`
         *
         * where:
         * - `P` is the current position.
         * - `Δt` is the time increment.
         * - `V` is the velocity.
         *
         * @param p The current position of the car in meters (m).
         * @param dt The time increment in seconds (s).
         * @param velocity The velocity of the car in meters per second (m/s).
         * @return The updated position of the car in meters (m).
         */
        fun pNew(p: Double, dt: Double, velocity: Double): Double {
            return p + dt * velocity
        }

        //Chapter 4: Engine Torque
        /**
         * 4.1 Calculating angular velocity from RPM
         *
         * Calculates the angular velocity of the engine based on the engine's revolutions per minute (RPM).
         *
         * The angular velocity of the engine in radians per second (rad/s) is obtained by multiplying
         * the engine turnover rate by 2π and dividing by 60.
         *
         * The formula to calculate the angular velocity is as follows:
         *
         * `ωe = (2 * π * RPM) / 60`
         *
         * where:
         * - `RPM` is the engine revolutions per minute.
         *
         * @param rpm The engine's revolutions per minute (RPM).
         * @return The angular velocity of the engine in radians per second (rad/s).
         */
        fun angularVelocity(rpm: Int): Double {
            return (2 * PI * rpm) / 60
        }

        /**
         * 4.1 Gears and Wheel Torque
         *
         * Calculates the wheel torque based on the engine torque, gear ratio, and final drive ratio.
         *
         * The torque applied to the wheels is not the same as the engine torque because the engine torque passes through a transmission before it is applied to the wheels.
         *
         * The formula to calculate the wheel torque is as follows:
         *
         * `T_w = T_e * gk * G`
         *
         * where:
         * - `T_w` is the wheel torque
         * - `T_e` is the engine torque.
         * - `gk` is the gear ratio of the current gear.
         * - `G` is the final drive ratio.
         *
         * @param te The torque produced by the engine in Newton-meters (Nm).
         * @param gk The gear ratio of the current gear.
         * @param g The final drive ratio.
         * @return The torque applied to the wheels in Newton-meters (Nm).
         */
        fun tw(te: Double, gk: Double, g: Double): Double {
            return te * gk * g
        }

        /**
         * 4.1 Gears and Wheel Torque
         *
         * Calculates the angular velocity of the wheels based on the engine's RPM, gear ratio, and final drive ratio.
         *
         * The relationship between the engine turnover rate and the wheel angular velocity is determined by the gear ratio and the final drive ratio.
         *
         * The formula to calculate the wheel angular velocity is as follows:
         *
         * `ω_wheel = (2 * π * engineRpm) / (60 * gear_ratio * final_drive_ratio)`
         *
         * where:
         * - `engineRpm` is the engine revolutions per minute.
         * - `gear_ratio` is the gear ratio of the current gear.
         * - `final_drive_ratio` is the final drive ratio.
         *
         * @param engineRpm The engine's revolutions per minute (RPM).
         * @param gearRatio The gear ratio of the current gear.
         * @param finalDriveRatio The final drive ratio.
         * @return The angular velocity of the wheels in radians per second (rad/s).
         */
        fun calculateWheelAngularVelocity(engineRpm: Int, gearRatio: Double, finalDriveRatio: Double): Double {
            return (2 * PI * engineRpm) / (60 * gearRatio * finalDriveRatio)
        }

        /**
         * TODO this function needs to be redone and I dont want to do it rn (Ido)
         * 4.1 Gears and Wheel Torque
         *
         * Calculates the translational velocity of the car based on the engine's RPM, gear ratio, final drive ratio, and wheel radius.
         *
         * The translational velocity of the car is related to the wheel angular velocity and the wheel radius.
         *
         * The formula to calculate the translational velocity is as follows:
         *
         * `V = R_wheel * ω_wheel`
         *
         * where:
         * - `ω_wheel = (2 * π * engineRpm) / (60 * gear_ratio * final_drive_ratio)` (see calculateWheelAngularVelocity)
         * - `R_wheel` is the radius of the wheel.
         *
         * @param engineRpm The engine's revolutions per minute (RPM).
         * @param gearRatio The gear ratio of the current gear.
         * @param finalDriveRatio The final drive ratio.
         * @param wheelRadius The radius of the wheel in meters (m).
         * @return The translational velocity of the car in meters per second (m/s).
         * @see calculateWheelAngularVelocity
         */
        fun calculateTranslationalVelocity(engineRpm: Int, gearRatio: Double, finalDriveRatio: Double, wheelRadius: Double): Double {
            // Replicate the logic of calculateWheelAngularVelocity directly here
            val wheelAngularVelocity = (2 * PI * engineRpm) / (60 * gearRatio * finalDriveRatio)
            return wheelRadius * wheelAngularVelocity
        }

        //Chapter 5: Cornering
        /**
         * 5.1 Cornering force
         *
         * Calculates the cornering force acting on a car's tires during a turn.
         *
         * In high-speed cornering, the tires develop lateral forces, also known as cornering forces.
         * This force depends on the slip angle (alpha), which is the angle between the tire’s heading and its direction of travel,
         * and the weight on that tire.
         *
         * The formula to calculate the force is as follows:
         *
         * `F_cornering = C_a * α`
         *
         * where:
         * - `C_a` is the cornering stiffness.
         * - `α` is the slip angle.
         *
         * @param ca The cornering stiffness, a constant representing the tire's resistance to lateral deflection.
         * @param alpha The slip angle in radians.
         * @return The cornering force acting on the car's tires in Newtons (N).
         */
        fun fCornering(ca: Double, alpha: Double): Double {
            //Where ca is known as the cornering stiffness
            return ca * alpha
        }

        /**
         * 5.1 Slip angle for the front wheels
         *
         * Calculates the slip angle for the front wheels of the car.
         *
         * The slip angle for the front wheels is determined by the lateral velocity, longitudinal velocity,
         * angular velocity, distance from the center of gravity to the front axle, and the steering angle.
         *
         * The formula to calculate the slip angle is as follows:
         *
         * `α_front = atan((V_lat + ω * b) / V_long) - σ * sign(V_long)`
         *
         * where:
         * - `V_lat` is the lateral velocity.
         * - `V_long` is the longitudinal velocity.
         * - `ω` is the angular velocity of the car.
         * - `b` is the distance from the center of gravity to the front axle.
         * - `σ` is the steering angle.
         *
         * @param vLat The lateral velocity of the car in meters per second (m/s).
         * @param vLong The longitudinal velocity of the car in meters per second (m/s).
         * @param omega The angular velocity of the car in radians per second (rad/s).
         * @param b The distance from the center of gravity to the front axle in meters (m).
         * @param sigma The steering angle in radians.
         * @return The slip angle for the front wheels in radians.
         */
        fun alphaFront(vLat: Double, vLong: Double, omega: Double, b: Double, sigma: Double): Double {
            return atan((vLat + omega * b) / vLong) - sigma * sign(vLong)
        }

        /**
         * 5.1 Slip angle for the rear wheels
         *
         * Calculates the slip angle for the rear wheels of the car.
         *
         * The slip angle for the rear wheels is determined by the lateral velocity, longitudinal velocity,
         * angular velocity, and the distance from the center of gravity to the rear axle.
         *
         * The formula to calculate the slip angle is as follows:
         *
         * `α_rear = atan((V_lat - ω * c) / V_long)`
         *
         * where:
         * - `V_lat` is the lateral velocity.
         * - `V_long` is the longitudinal velocity.
         * - `ω` is the angular velocity of the car.
         * - `c` is the distance from the center of gravity to the rear axle.
         *
         * @param vLat The lateral velocity of the car in meters per second (m/s).
         * @param vLong The longitudinal velocity of the car in meters per second (m/s).
         * @param omega The angular velocity of the car in radians per second (rad/s).
         * @param c The distance from the center of gravity to the rear axle in meters (m).
         * @return The slip angle for the rear wheels in radians.
         */
        fun alphaRear(vLat: Double, vLong: Double, omega: Double, c: Double): Double {
            return atan((vLat - omega * c) / vLong)
        }

        /**
         * 5.1 Lateral force
         *
         * Calculates the lateral force acting on a car's tires during cornering.
         *
         * The lateral force depends on the cornering stiffness, slip angle, load on the tire, maximum friction force, and whether the wheel is slipping.
         *
         * The calculation involves the following steps:
         * 1. Calculate initial lateral force.
         * 2. Cap to maximum normalized friction force.
         * 3. Multiply by load.
         * 4. Adjust for wheel slip if necessary.
         *
         * The formula to calculate the initial lateral force is as follows:
         *
         * `F_lat = C_a * α`
         *
         * where:
         * - `C_a` is the cornering stiffness.
         * - `α` is the slip angle.
         *
         * @param ca The cornering stiffness, a constant representing the tire's resistance to lateral deflection.
         * @param alpha The slip angle in radians.
         * @param load The load on the tire in Newtons (N).
         * @param maxFrictionForce The maximum normalized friction force.
         * @param wheelSlip A boolean indicating whether the wheel is slipping.
         * @return The lateral force acting on the car's tires in Newtons (N).
         */
        fun fLat(ca: Double, alpha: Double, load: Double, maxFrictionForce: Double, wheelSlip: Boolean): Double {
            // Step 1: Calculate initial lateral force
            var flat = ca * alpha //ca

            // Step 2: Cap to maximum normalized friction force
            flat = min(flat, maxFrictionForce)

            // Step 3: Multiply by load
            flat *= load

            // Step 4: Adjust for wheel slip
            if (wheelSlip) {
                flat *= 0.5
            }

            return flat
        }

        /**
         * 5.1 Torque due to lateral forces
         *
         * Calculates the torque on the car body due to lateral forces on the front and rear tires during cornering.
         *
         * The lateral forces acting on the front and rear tires create a torque that causes the car body to turn.
         * This torque depends on the cornering forces, the distances from the center of gravity to the front and rear axles,
         * and the steering angle.
         *
         * The formula to calculate the torque is as follows:
         *
         * `Torque = cos(σ) * F_lat,front * b - F_lat,rear * c`
         *
         * where:
         * - `F_lat,front` is the lateral force on the front tires.
         * - `F_lat,rear` is the lateral force on the rear tires.
         * - `b` is the distance from the center of gravity to the front axle.
         * - `c` is the distance from the center of gravity to the rear axle.
         * - `σ` is the steering angle.
         *
         * @param flatFront The lateral force on the front tires in Newtons (N).
         * @param flatRear The lateral force on the rear tires in Newtons (N).
         * @param b The distance from the center of gravity to the front axle in meters (m).
         * @param c The distance from the center of gravity to the rear axle in meters (m).
         * @param sigma The steering angle in radians.
         * @return The torque on the car body in Newton-meters (Nm).
         */
        fun calculateTorque(flatFront: Double, flatRear: Double, b: Double, c: Double, sigma: Double): Double {
            return cos(sigma) * flatFront * b - flatRear * c
        }

        //Chapter 6: Implementing car physics engine (part 1)

    }
}
