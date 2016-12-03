package com.nexuslink.mydiary.Presenter;

import android.support.v4.app.Fragment;

import com.nexuslink.mydiary.View.IView;

/**
 * Created by Rye on 2016/12/3.
 */

public class PresenterImpl implements IPresenter{
    private IView iView;
    public PresenterImpl(IView iView) {
        this.iView = iView;
    }

    @Override
    public void changeFragment(Fragment fragment) {
        iView.change(fragment);
    }
}
