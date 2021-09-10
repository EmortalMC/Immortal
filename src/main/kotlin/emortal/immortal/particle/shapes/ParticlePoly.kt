package emortal.immortal.particle.shapes

import net.minestom.server.network.packet.server.play.ParticlePacket

class ParticlePoly(val lines: Collection<ParticleLine>) : ParticleShape {

    companion object {
        fun builder(particleSingle: ParticleSingle): PolygonBuilder {
            return PolygonBuilder(particleSingle)
        }
    }

    override val packets: Collection<ParticlePacket>
        get() = lines.flatMap { it.packets }

}