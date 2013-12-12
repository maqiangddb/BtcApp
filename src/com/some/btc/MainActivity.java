package com.some.btc;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends Activity
{

    ListView _dataList;
    ArrayAdapter _dataAdapter;

    String[] KEYS = {
            "platform",
            "last",
            "buy",
            "sell",
            "vol"
    };

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

        try {
            fetchData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
        _dataList = (ListView) findViewById(R.id.data_list);
        _dataAdapter = new DataList(this, R.layout.list_item);
        _dataList.setAdapter(_dataAdapter);
    }

    private void fetchData() throws JSONException {
        BtcInfoFetcher fetcher = new BtcInfoFetcher();
        BtcInfo btcInfo = fetcher.fetch("btcchina");
        DataObject btc_china = new DataObject(
                "btc china",
                btcInfo.getLastPrice(),
                btcInfo.getBuyPrice(),
                btcInfo.getSellPrice(),
                btcInfo.getVolume()
        );
        
        BtcInfo mgBtcInfo = fetcher.fetch("MtGox");
        DataObject mt_gox = new DataObject(
                "MtGox",
                mgBtcInfo.getLastPrice(),
                mgBtcInfo.getBuyPrice(),
                mgBtcInfo.getSellPrice(),
                mgBtcInfo.getVolume()
        );
        _dataAdapter.add(btc_china);
        _dataAdapter.add(mt_gox);
    }

    private class DataList extends ArrayAdapter<DataObject> {

        private int _itemLayout;

        public DataList(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
            _itemLayout = textViewResourceId;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            DataObject data = getItem(position);
            View result = inflater.inflate(_itemLayout, new LinearLayout(this.getContext()), true);
            TextView platform = (TextView) result.findViewById(R.id.platform);
            TextView last_price = (TextView) result.findViewById(R.id.last_price);
            TextView buy_price = (TextView) result.findViewById(R.id.buy_price);
            TextView sell_price = (TextView) result.findViewById(R.id.sell_price);
            TextView volume = (TextView) result.findViewById(R.id.volumn);

            platform.setText(data._platform);
            last_price.setText(data._lastPrice);
            buy_price.setText(data._buyPrice);
            sell_price.setText(data._sellPrice);
            volume.setText(data._volume);
            return result;
        }
    }

    private class DataObject {
        public String _platform;
        public String _lastPrice;
        public String _buyPrice;
        public String _sellPrice;
        public String _volume;

        DataObject(String platform, String lastPrice, String buyPrice, String sellPrice, String volumn) {
            _platform = platform;
            _lastPrice = lastPrice;
            _buyPrice = buyPrice;
            _sellPrice = sellPrice;
            _volume = volumn;
        }

    }

}
