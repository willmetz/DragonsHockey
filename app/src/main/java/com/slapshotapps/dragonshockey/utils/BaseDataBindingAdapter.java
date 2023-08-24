package com.slapshotapps.dragonshockey.utils;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.slapshotapps.dragonshockey.BR;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseDataBindingAdapter
        extends RecyclerView.Adapter<BaseDataBindingAdapter.MyViewHolder> {

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(
                layoutInflater, viewType, parent, false);
        return new MyViewHolder(binding);
    }

    public void onBindViewHolder(MyViewHolder holder, int position) {
        Object obj = getObjForPosition(position);
        holder.bind(obj);
    }

    @Override
    public int getItemViewType(int position) {
        return getLayoutIdForPosition(position);
    }

    protected abstract Object getObjForPosition(int position);

    protected abstract int getLayoutIdForPosition(int position);

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private final ViewDataBinding binding;

        MyViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Object obj) {
            binding.setVariable(BR.item, obj);
            binding.executePendingBindings();
        }
    }
}
