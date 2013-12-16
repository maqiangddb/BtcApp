package com.some.btc;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;

@SuppressLint("NewApi")
public class MainActivity extends Activity
{

    ListView _dataList;
    ArrayAdapter<BtcInfo> _dataAdapter;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        initUI();
    }

    @Override
    public void onResume() {
        super.onResume();
		fetchData();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initUI() {
        setContentView(R.layout.main);
		setTitleTextSize();
        _dataList = (ListView) findViewById(R.id.data_list);
        _dataAdapter = new DataList(this, R.layout.list_item);
        _dataList.setAdapter(_dataAdapter);
    }

	private void setTitleTextSize() {
		LinearLayout ll = (LinearLayout) findViewById(R.id.title);
		for	(int i=0; i < ll.getChildCount(); i++) {
			TextView tv = (TextView) ll.getChildAt(i);
			tv.setTextSize(12);
		}
	}

    private void fetchData() {
        new FetchTask().execute();
        Log.d("xiaochi", "- fetch task has started");
    }
    
    @SuppressLint("NewApi")
	class FetchTask extends AsyncTask<Object, Object, Object> {
    	BtcInfo btcInfo = null;
        BtcInfo mgBtcInfo = null;

		@Override
		protected Object doInBackground(Object... arg0) {
			BtcInfoFetcher fetcher = new BtcInfoFetcher();
			try {
				btcInfo = fetcher.fetch("btcchina");
				mgBtcInfo = fetcher.fetch("MtGox");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			publishProgress();
			return null;
		}

		@Override
		protected void onProgressUpdate(Object... values) {
			super.onProgressUpdate(values);
			_dataAdapter.clear();
	        _dataAdapter.add(btcInfo);
	        _dataAdapter.add(mgBtcInfo);
		}
    	
    };

    private class DataList extends ArrayAdapter<BtcInfo> {

        private int _itemLayout;

        public DataList(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
            _itemLayout = textViewResourceId;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            BtcInfo info = getItem(position);
            View result = inflater.inflate(_itemLayout, new LinearLayout(this.getContext()), true);
            TextView platform = (TextView) result.findViewById(R.id.platform);
            TextView last_price = (TextView) result.findViewById(R.id.last_price);
            TextView buy_price = (TextView) result.findViewById(R.id.buy_price);
            TextView sell_price = (TextView) result.findViewById(R.id.sell_price);
            TextView volume = (TextView) result.findViewById(R.id.volumn);

            platform.setText(info.getName());
            last_price.setText(info.getLastPrice());
            buy_price.setText(info.getBuyPrice());
            sell_price.setText(info.getSellPrice());
            volume.setText(info.getVolume());
            return result;
        }
    }

}
