package weapons.audio;


public class Sounds {

    private static final String SOUND_RESOURCE_LOCATION = "MoreOres/sound/";
    private static final String SOUND_PREFIX = "moreores.sound.";

    public static String[] soundFiles = { 
    	SOUND_RESOURCE_LOCATION + "ElectrokarLiving.ogg",
    	SOUND_RESOURCE_LOCATION + "MolratHurt.wav",
    	SOUND_RESOURCE_LOCATION + "MolratMinerDeath.wav",
    	SOUND_RESOURCE_LOCATION + "ElectrokarFire.wav",
    	SOUND_RESOURCE_LOCATION + "MolratMinerLiving.ogg",
    	SOUND_RESOURCE_LOCATION + "Explosion.ogg",
    	SOUND_RESOURCE_LOCATION + "AinchentShipFly.wav",
    	SOUND_RESOURCE_LOCATION + "EnderGunFire.wav" };

    public static final String ELECTROKARLIVING = SOUND_PREFIX + "electrokarliving";
    public static final String MOLRATHURT = SOUND_PREFIX + "molrathurt";
    public static final String MOLRATDEATH = SOUND_PREFIX + "molratdeath";
    public static final String ELECTROKARFIRE = SOUND_PREFIX + "electrokarfire";
    public static final String MOLRATLIVING = SOUND_PREFIX + "molratliving";
    public static final String EXPLOSION = SOUND_PREFIX + "explosion";
    public static final String AINCHENTSHIPFLY = SOUND_PREFIX + "ainchentshipfly";
    public static final String ENDERGUNFIRE = SOUND_PREFIX + "endergunfire";

}
