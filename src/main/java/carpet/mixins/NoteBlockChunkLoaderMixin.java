package carpet.mixins;

import carpet.CarpetSettings;
import carpet.logging.logHelpers.NoteChunkLoaderDisplay;
import carpet.utils.NoteBlockChunkLoader;
import net.minecraft.block.BlockState;
import net.minecraft.block.NoteBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NoteBlock.class)
public abstract class NoteBlockChunkLoaderMixin {
    @Shadow @Final public static IntProperty NOTE;

    @Shadow public abstract BlockState getPlacementState(ItemPlacementContext ctx);

    @Inject(method = "playNote", at = @At("HEAD"))
    private void loadChunk(World world, BlockPos pos, CallbackInfo info)
    {

        if(CarpetSettings.noteBlockChunkLoader)
        {
            int note = world.getBlockState(pos).get(NoteBlock.NOTE);

            if(CarpetSettings.noteBlockChunkLoaderNote == -1 || note == CarpetSettings.noteBlockChunkLoaderNote)
            {
                ChunkPos cp = new ChunkPos(pos.getX() >> 4, pos.getZ() >> 4);

                ChunkTicketType ctt = ChunkTicketType.create("note_block",NoteBlockChunkLoader.LongComparator, CarpetSettings.noteBlockChunkLoaderTick);

                ((ServerWorld) world).getChunkManager().addTicket(ctt, cp, CarpetSettings.noteBlockChunkLoaderRadius, cp);

                NoteChunkLoaderDisplay.msgPrint(cp, world);

            }
        }
    }
}
