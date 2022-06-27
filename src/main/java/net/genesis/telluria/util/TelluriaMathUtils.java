package net.genesis.telluria.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class TelluriaMathUtils {

    /**
     * Returns the distance between two block positions
     * 
     * @param pos1 [BlockPos] the first position
     * @param pos2 [BlockPos] the second position 
     * @return the distance between pos1 and pos2
     */
    public static double getDistance(BlockPos pos1, BlockPos pos2) {
        double deltaX = pos1.getX() - pos2.getX();
        double deltaY = pos1.getY() - pos2.getY();
        double deltaZ = pos1.getZ() - pos2.getZ();

        return Math.sqrt((deltaX * deltaX) + (deltaY * deltaY) + (deltaZ * deltaZ));
    }

    /**
     * Returns the distance between two block positions
     * 
     * @param pos1 [BlockPos] the first position
     * @param pos2 [BlockPos] the second position 
     * @return the distance between pos1 and pos2
     */
    public static double getDistance(Vec3d pos1, Vec3d pos2) {
        double deltaX = pos1.getX() - pos2.getX();
        double deltaY = pos1.getY() - pos2.getY();
        double deltaZ = pos1.getZ() - pos2.getZ();

        return Math.sqrt((deltaX * deltaX) + (deltaY * deltaY) + (deltaZ * deltaZ));
    }
}
