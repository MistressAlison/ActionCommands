package ActionCommands;

import basemod.*;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.UIStrings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ActionCommands.util.TextureLoader;

@SpireInitializer
public class ActionCommands implements PostInitializeSubscriber, EditStringsSubscriber {
    public static final Logger logger = LogManager.getLogger(ActionCommands.class.getName());
    private static String modID;

    //This is for the in-game mod settings panel.
    private static final String MODNAME = "Action Commands";
    private static final String AUTHOR = "Mistress Alison";
    private static final String DESCRIPTION = "Adds Action Commands";
    
    // =============== INPUT TEXTURE LOCATION =================
    
    //Mod Badge - A small icon that appears in the mod settings menu next to your mod.
    public static final String BADGE_IMAGE = "ActionCommandsResources/images/Badge.png";
    
    // =============== /INPUT TEXTURE LOCATION/ =================
    
    
    // =============== SUBSCRIBE, INITIALIZE =================
    
    public ActionCommands() {
        logger.info("Subscribe to BaseMod hooks");
        
        BaseMod.subscribe(this);
      
        setModID("ActionCommands");
        
        logger.info("Done subscribing");

        logger.info("Adding mod settings");

        //TODO mod settings here

        logger.info("Done adding mod settings");
        
    }
    
    public static void setModID(String ID) {
        modID = ID;
    }
    
    public static String getModID() {
        return modID;
    }

    public static void initialize() {
        logger.info("========================= Initializing Action Commands. =========================");
        ActionCommands actionCommands = new ActionCommands();
        logger.info("========================= /Action Commands Initialized/ =========================");
    }
    
    // ============== /SUBSCRIBE, INITIALIZE/ =================
    
    // =============== POST-INITIALIZE =================
    
    @Override
    public void receivePostInitialize() {

        logger.info("Loading badge image and mod options");
        
        // Load the Mod Badge
        Texture badgeTexture = TextureLoader.getTexture(BADGE_IMAGE);

        // Create the Mod Menu
        ModPanel settingsPanel = new ModPanel();

        //TODO load mod settings here
        
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);

        logger.info("Done loading badge Image and mod options");
    }
    
    // =============== / POST-INITIALIZE/ =================

    // ================ LOAD THE LOCALIZATION ===================

    private String loadLocalizationIfAvailable(String fileName) {
        if (!Gdx.files.internal(getModID() + "Resources/localization/" + Settings.language.toString().toLowerCase()+ "/" + fileName).exists()) {
            logger.info("Language: " + Settings.language.toString().toLowerCase() + ", not currently supported for " +fileName+".");
            return "eng" + "/" + fileName;
        } else {
            logger.info("Loaded Language: "+ Settings.language.toString().toLowerCase() + ", for "+fileName+".");
            return Settings.language.toString().toLowerCase() + "/" + fileName;
        }
    }

    // ================ /LOAD THE LOCALIZATION/ ===================

    // ================ LOAD STRINGS ===================

    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }

    @Override
    public void receiveEditStrings() {
        // UIStrings
        BaseMod.loadCustomStringsFile(UIStrings.class,
                getModID() + "Resources/localization/"+loadLocalizationIfAvailable("ActionCommands-UI-Strings.json"));
    }

    // ================ /LOAD STRINGS/ ===================
}
