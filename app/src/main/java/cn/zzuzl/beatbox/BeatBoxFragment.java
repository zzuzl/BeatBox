package cn.zzuzl.beatbox;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cn.zzuzl.beatbox.databinding.FragmentBeatBoxBinding;
import cn.zzuzl.beatbox.databinding.ListItemSoundBinding;

/**
 * Created by zhanglei53 on 2018/1/30.
 */

public class BeatBoxFragment extends Fragment {
    private BeatBox mBeatBox;

    private void BeatBoxFragment() {
    }

    public static BeatBoxFragment newInstance() {
        return new BeatBoxFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBeatBox = new BeatBox(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentBeatBoxBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_beat_box, container, false);

        // 网格布局
        binding.recycleView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        binding.recycleView.setAdapter(new SoundAdapter(mBeatBox.getSounds()));
        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBeatBox.release();
    }

    /**
     * holder
     */
    private class SoundHolder extends RecyclerView.ViewHolder {
        private ListItemSoundBinding mSoundBinding;

        public SoundHolder(ListItemSoundBinding soundBinding) {
            super(soundBinding.getRoot());
            mSoundBinding = soundBinding;
            mSoundBinding.setViewModel(new SoundViewModel(mBeatBox));
        }

        // 绑定
        public void bind(Sound sound) {
            mSoundBinding.getViewModel().setSound(sound);
            mSoundBinding.executePendingBindings();
        }
    }

    /**
     * adapter
     */
    private class SoundAdapter extends RecyclerView.Adapter<SoundHolder> {
        private List<Sound> mSounds = null;

        public SoundAdapter(List<Sound> sounds) {
            mSounds = sounds;
        }

        @Override
        public SoundHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            ListItemSoundBinding binding = DataBindingUtil.inflate(inflater, R.layout.list_item_sound, parent, false);
            return new SoundHolder(binding);
        }

        @Override
        public void onBindViewHolder(SoundHolder holder, int position) {
            holder.bind(mSounds.get(position));
        }

        @Override
        public int getItemCount() {
            return mSounds.size();
        }
    }
}
