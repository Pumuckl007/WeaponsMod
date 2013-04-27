package weapons.audio;

import weapons.Weapons;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;

public class Weapons_Sounds
{
//   	int num = 0;
//    @ForgeSubscribe
//    public void onSoundLoad(SoundLoadEvent event)
//    {
// 
//        try 
//        {
//            event.manager.soundPoolSounds.addSound("moreores/sound/electrokarliving.ogg", MoreOres.class.getResource("/MoreOres/sound/ElectrokarLiving.ogg")); 
//            event.manager.soundPoolSounds.addSound("moreores/sound/molrathurt.wav", MoreOres.class.getResource("/MoreOres/sound/MolratHurt.wav")); 
//            event.manager.soundPoolSounds.addSound("moreores/sound/molratdeath.wav", MoreOres.class.getResource("/MoreOres/sound/MolratMinerDeath.wav")); 
//            event.manager.soundPoolSounds.addSound("moreores/sound/electrokarfire.wav", MoreOres.class.getResource("/MoreOres/sound/ElectrokarFire.wav")); 
//            event.manager.soundPoolSounds.addSound("moreores/sound/molratliving.ogg", MoreOres.class.getResource("/MoreOres/sound/MolratMinerLiving.ogg")); 
//            event.manager.soundPoolSounds.addSound("moreores/sound/molratliving.ogg", MoreOres.class.getResource("/MoreOres/sound/MolratMinerLiving.ogg")); 
//            event.manager.soundPoolSounds.addSound("moreores/sound/explosion.ogg", MoreOres.class.getResource("/MoreOres/sound/Explosion.ogg")); 
//            event.manager.soundPoolSounds.addSound("moreores/sound/ainchentshipfly.wav", MoreOres.class.getResource("/MoreOres/sound/AinchentShipFly.wav")); 
//            event.manager.soundPoolSounds.addSound("moreores/sound/endergunfire.wav", MoreOres.class.getResource("/MoreOres/sound/EnderGunFire.wav")); 
//            System.out.println("******************** loaded sounds");
//
//        } 
//        catch (Exception e)
//        {
//            System.err.println("Failed to register one or more sounds.");
//        }
//    	
//    }
    @ForgeSubscribe
    public void onSoundLoad(SoundLoadEvent event) {

        // For each custom sound file we have defined in Sounds
        for (String soundFile : Sounds.soundFiles) {
            // Try to add the custom sound file to the pool of sounds
            try {
                event.manager.soundPoolSounds.addSound(soundFile, this.getClass().getResource("/" + soundFile));
            }
            // If we cannot add the custom sound file to the pool, log the exception
            catch (Exception e) {
            	Weapons.log( "Failed loading sound file: " + soundFile);
            }
        }
    }
    
}
