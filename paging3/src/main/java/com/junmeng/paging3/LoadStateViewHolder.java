package com.junmeng.paging3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.LoadState;
import androidx.paging.LoadStateAdapter;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

class LoadStateViewHolder extends RecyclerView.ViewHolder {
  private ProgressBar mProgressBar;
  private TextView mErrorMsg;
  private Button mRetry;

  LoadStateViewHolder(
    @NonNull ViewGroup parent,
    @NonNull View.OnClickListener retryCallback) {
    super(LayoutInflater.from(parent.getContext())
      .inflate(R.layout.item_load_state, parent, false));

    mProgressBar = itemView.findViewById(R.id.progress);
    mErrorMsg = itemView.findViewById(R.id.message);
  }

  public void bind(LoadState loadState) {
    if (loadState instanceof LoadState.Error) {
      LoadState.Error loadStateError = (LoadState.Error) loadState;
      mErrorMsg.setText(loadStateError.getError().getLocalizedMessage());
    }
    mProgressBar.setVisibility(loadState instanceof LoadState.Loading
      ? View.VISIBLE : View.GONE);
//    mRetry.setVisibility(loadState instanceof LoadState.Error
//      ? View.VISIBLE : View.GONE);
    mErrorMsg.setVisibility(loadState instanceof LoadState.Error
      ? View.VISIBLE : View.GONE);
  }
}

// Adapter that displays a loading spinner when
// state instanceOf LoadState.Loading, and an error message and
// retry button when state instanceof LoadState.Error.
class ExampleLoadStateAdapter extends LoadStateAdapter<LoadStateViewHolder> {
  private View.OnClickListener mRetryCallback;

  ExampleLoadStateAdapter(View.OnClickListener retryCallback) {
    mRetryCallback = retryCallback;
  }


  @NotNull
  @Override
  public LoadStateViewHolder onCreateViewHolder(@NotNull ViewGroup parent,
    @NotNull LoadState loadState) {
    return new LoadStateViewHolder(parent, mRetryCallback);
  }

  @Override
  public void onBindViewHolder(@NotNull LoadStateViewHolder holder,
    @NotNull LoadState loadState) {
    holder.bind(loadState);
  }
}