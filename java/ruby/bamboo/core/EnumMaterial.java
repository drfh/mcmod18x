package ruby.bamboo.core;

import net.minecraft.block.material.Material;

public enum EnumMaterial {
	AIR(Material.air), GRASS(Material.grass), GROUND(Material.ground), WOOD(Material.wood), ROCK(Material.rock), IRON(Material.iron), ANVIL(Material.anvil), WATER(Material.water), LAVA(Material.lava), LEAVES(
			Material.leaves), PLANTS(Material.plants), VINE(Material.vine), SPONGE(Material.sponge), CLOTH(Material.cloth), FIRE(Material.fire), SAND(Material.sand), CIRCUITS(Material.circuits), CARPET(
			Material.carpet), GLASS(Material.glass), REDSTONELIGHT(Material.redstoneLight), TNT(Material.tnt), CORAL(Material.coral), ICE(Material.ice), PACKEDICE(Material.packedIce), SNOW(
			Material.snow), CRAFTEDSNOW(Material.craftedSnow), CACTUS(Material.cactus), CLAY(Material.clay), GOURD(Material.gourd), DRAGONEGG(Material.dragonEgg), PORTAL(Material.portal), CAKE(
			Material.cake), WEB(Material.web);
	public Material MATERIAL;
	
	EnumMaterial(Material material) {
		this.MATERIAL = material;
	}

}
