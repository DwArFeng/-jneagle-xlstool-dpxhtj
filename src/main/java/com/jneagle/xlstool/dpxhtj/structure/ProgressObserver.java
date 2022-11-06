package com.jneagle.xlstool.dpxhtj.structure;

import com.dwarfeng.dutil.basic.prog.Observer;

/**
 * 进度观察器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ProgressObserver extends Observer {

    void onProgressChanged(ProgressStatus progressStatus);
}
