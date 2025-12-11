package com.langpossible.core.plugin.tool;

import org.osgi.framework.BundleActivator;

public interface ToolContract extends BundleActivator {

    String getName();

    ToolOutput invoke(String action, ToolInput input);

}
