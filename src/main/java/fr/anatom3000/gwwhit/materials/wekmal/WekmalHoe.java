package fr.anatom3000.gwwhit.materials.wekmal;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.HoeItem;
import fr.anatom3000.gwwhit.CustomItemGroups;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
public class WekmalHoe extends HoeItem {
public WekmalHoe(ToolMaterial material) {super(material, 8, 0.3336409076787362f, new FabricItemSettings().group(CustomItemGroups.GWWHITGroup));}

}