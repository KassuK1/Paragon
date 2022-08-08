package com.paragon.api.event.render.world

import me.wolfsurge.cerauno.event.CancellableEvent
import net.minecraft.util.math.BlockPos

/**
 * @author Surge
 */
class SideRenderBlockEvent(val pos: BlockPos) : CancellableEvent() {

    var returnValue = false

}