/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.objects;

/**
 *
 * @author Home
 */
public class SiteTemplate {

    private boolean use;
    private String title;
    private String name;
    private String script;
    
    public SiteTemplate(boolean use, String title, String name, String script){
        this.use = use;
        this.title = title;
        this.name = name;
        this.script = script;
    }

    /**
     * @return the use
     */
    public boolean isUse() {
        return use;
    }

    /**
     * @param use the use to set
     */
    public void setUse(boolean use) {
        this.use = use;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the script
     */
    public String getScript() {
        return script;
    }

    /**
     * @param script the script to set
     */
    public void setScript(String script) {
        this.script = script;
    }
    
}
