package scripting;

import handling.channel.ChannelServer;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import tools.FileoutputUtil;

public class EventScriptManager extends AbstractScriptManager
{
    private final Map<String, EventEntry> events;
    private final AtomicInteger runningInstanceMapId;
    
    public final int getNewInstanceMapId() {
        return this.runningInstanceMapId.addAndGet(1);
    }
    
    public EventScriptManager(final ChannelServer cserv, final String[] scripts) {
        this.events = new LinkedHashMap<String, EventEntry>();
        this.runningInstanceMapId = new AtomicInteger(0);
        for (final String script : scripts) {
            if (!script.equals("")) {
                final Invocable iv = this.getInvocable("event"+ File.separator + script + ".js", null);
                if (iv != null) {
                    this.events.put(script, new EventEntry(script, iv, new EventManager(cserv, iv, script)));
                }
            }
        }
    }
    
    public final EventManager getEventManager(final String event) {
        final EventEntry entry = this.events.get(event);
        if (entry == null) {
            return null;
        }
        return entry.em;
    }
    
    public final void init() {
        for (final EventEntry entry : this.events.values()) {
            try {
                ((ScriptEngine)entry.iv).put("em", entry.em);
                entry.iv.invokeFunction("init", null);
            }
            catch (Exception ex) {
                System.out.println("Error initiating event: " + entry.script + ":" + ex);
                FileoutputUtil.log(FileoutputUtil.ScriptEx_Log, "Error initiating event: " + entry.script + ":" + ex);
            }
        }
    }
    
    public final void cancel() {
        for (final EventEntry entry : this.events.values()) {
            entry.em.cancel();
        }
    }
    
    private static class EventEntry
    {
        public String script;
        public Invocable iv;
        public EventManager em;
        
        public EventEntry(final String script, final Invocable iv, final EventManager em) {
            this.script = script;
            this.iv = iv;
            this.em = em;
        }
    }
}
