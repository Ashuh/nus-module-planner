package com.ashuh.nusmoduleplanner.data.model.nusmods.module.prereqtree;

import java.util.Collection;

public interface Prerequisite {

    boolean isSatisfied(Collection<String> moduleCodes);
}
