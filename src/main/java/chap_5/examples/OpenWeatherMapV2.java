package chap_5.examples;

import common.CommonUtils;
import common.Log;
import common.OkHttpHelper;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OpenWeatherMapV2 {
    private static final String URL = "http://api.openweathermap.org/data/2.5/weather?q=London&APPID=";

    public void run() {
        CommonUtils.exampleStart();

        Observable<String> source = Observable.just(URL + "API key")
                .map(OkHttpHelper::getWithLog)
                .subscribeOn(Schedulers.io())
                .share()
                .observeOn(Schedulers.newThread());

        source.map(this::parseTemperature).subscribe(Log::it);
        source.map(this::parseCityName).subscribe(Log::it);
        source.map(this::parseCountry).subscribe(Log::it);

        CommonUtils.sleep(1000);
    }

    private String parseTemperature(String json) {
        return parse(json, "\"temp\":[0-9]*.[0-9]*");
    }

    private String parseCityName(String json) {
        return parse(json, "\"name\":\"[a-zA-Z]*\"");
    }

    private String parseCountry(String json) {
        return parse(json, "\"country\":\"[a-zA-Z]*\"");
    }

    private String parse(String json, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher match = pattern.matcher(json);
        if (match.find()) {
            return match.group();
        }
        return "N/A";
    }

    public static void main(String[] args) {
        OpenWeatherMapV2 demo = new OpenWeatherMapV2();
        demo.run();
    }
}
/*
RxCachedThreadScheduler-1 | debug = OkHttp call URL = http://api.openweathermap.org/data/2.5/weather...
RxNewThreadScheduler-3 | 1233 | value = "country":"GB"
RxNewThreadScheduler-2 | 1233 | value = "name":"London"
RxNewThreadScheduler-1 | 1233 | value = "temp":286.58
 */