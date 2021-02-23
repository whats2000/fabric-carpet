package carpet.utils;

import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.util.math.ChunkPos;

import java.util.Comparator;

public class NoteBlockChunkLoader {
    public static final Comparator<ChunkPos> LongComparator = Comparator.comparingLong(ChunkPos::toLong);
}
