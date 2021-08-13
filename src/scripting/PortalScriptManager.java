package scripting;

import client.MapleClient;
import server.MaplePortal;
import tools.FileoutputUtil;

import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class PortalScriptManager
{
    private static final PortalScriptManager instance;
    private static final ScriptEngineFactory sef;
    private final Map<String, PortalScript> scripts;
    
    public PortalScriptManager() {
        this.scripts = new HashMap<String, PortalScript>();
    }
    
    public static PortalScriptManager getInstance() {
        return PortalScriptManager.instance;
    }
    
    private PortalScript getPortalScript(final MapleClient c, final String scriptName) {
        if (this.scripts.containsKey(scriptName)) {
            this.scripts.clear();
            return this.scripts.get(scriptName);
        }
        String scriptsPath = System.getProperty("scripts_path");
        final File scriptFile = new File(scriptsPath+"scripts"+File.separator+"portal"+File.separator + scriptName + ".js");
        if (!scriptFile.exists()) {
            return null;
        }
        InputStream fr = null;
        final ScriptEngine portal = PortalScriptManager.sef.getScriptEngine();
        try {
            fr = new FileInputStream(scriptFile);
            final BufferedReader bf = new BufferedReader(new InputStreamReader(fr, EncodingDetect.getJavaEncode(scriptFile)));
            final CompiledScript compiled = ((Compilable)portal).compile(bf);
            compiled.eval();
        }
        catch (Exception e) {
            System.err.println("Error executing Portalscript: " + scriptName + ":" + e);
            FileoutputUtil.log(FileoutputUtil.ScriptEx_Log, "Error executing Portal script. (" + scriptName + ") " + e);
            if (fr != null) {
                try {
                    fr.close();
                }
                catch (IOException e2) {
                    System.err.println("ERROR CLOSING" + e2);
                }
            }
        }
        finally {
            if (fr != null) {
                try {
                    fr.close();
                }
                catch (IOException e3) {
                    System.err.println("ERROR CLOSING" + e3);
                }
            }
        }
        final PortalScript script = ((Invocable)portal).getInterface(PortalScript.class);
        this.scripts.put(scriptName, script);
        return script;
    }
    
    public void executePortalScript(final MaplePortal portal, final MapleClient c) {
        final PortalScript script = this.getPortalScript(c, portal.getScriptName());
        if (c.getPlayer().isGM()) {
            c.getPlayer().dropMessage("[系统提示]您已经建立与PortalScript:[" + portal.getScriptName() + ".js]的对话。");
        }
        if (script != null) {
            try {
                script.enter(new PortalPlayerInteraction(c, portal));
            }
            catch (Exception e) {
                System.err.println("Error entering Portalscript: " + portal.getScriptName() + ":" + e);
            }
        }
    }
    
    public void clearScripts() {
        this.scripts.clear();
    }
    
    static {
        instance = new PortalScriptManager();
        sef = new ScriptEngineManager().getEngineByName("javascript").getFactory();
    }
}
