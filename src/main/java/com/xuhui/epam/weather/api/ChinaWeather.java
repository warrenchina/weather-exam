package com.xuhui.epam.weather.api;

import com.google.gson.Gson;
import com.xuhui.epam.weather.model.WeatherVo;
import com.xuhui.epam.weather.tool.HttpClientUtil;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;


public class ChinaWeather {

    public Optional<Integer> getTemperature(String province, String city, String county)throws Exception{
        if(StringUtils.isEmpty(province)){
            throw new Exception("省份编码不能为空");
        }
        if(StringUtils.isEmpty(city)){
            throw new Exception("城市编码不能为空");
        }
        if(StringUtils.isEmpty(county)){
            throw new Exception("区县编码不能为空");
        }
        if(!checkProvince(province)){
            throw new Exception("省份编码不存在");
        }
        if(!checkCity(province,city)){
            throw new Exception("城市编码不存在");
        }
        if(!checkCounty(province,city,county)){
            throw new Exception("区县编码不存在");
        }
        String temperatureResult=getCountyTemperature(province,city,county);
        if(StringUtils.isEmpty(temperatureResult)){
             throw new Exception("暂无您需要的城市天气，请稍后再试");
        }else {
            Optional<Integer> optionalInt = Optional.ofNullable(Integer.valueOf(temperatureResult));
            return optionalInt;
        }

    }

    private Boolean checkProvince(String province){
        String url="http://www.weather.com.cn/data/city3jdata/china.html";
        try {
            String result=HttpClientUtil.doGet(url,3000,null,null);
            if(!StringUtils.isEmpty(result)&&result.length()>2){
                List<String> resultList= new ArrayList<>(Arrays.asList(result.substring(1,result.length()-1).split(",")));
                if(resultList.stream().filter(pro->pro.contains(province)).findAny().isPresent()){
                    return true;
                }
            }else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {

        }
        return false;
    }
    private Boolean checkCity(String province,String city){
        String url="http://www.weather.com.cn/data/city3jdata/provshi/"+province+".html";
        try {
            String result=HttpClientUtil.doGet(url,3000,null,null);
            if(!StringUtils.isEmpty(result)&&result.length()>2){
                List<String> resultList= new ArrayList<>(Arrays.asList(result.substring(1,result.length()-1).split(",")));
                if(resultList.stream().filter(pro->pro.contains(city)).findAny().isPresent()){
                    return true;
                }
            }else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {

        }
        return false;

    }

    private Boolean checkCounty(String province, String city, String county){
        String url="http://www.weather.com.cn/data/city3jdata/station/"+province+city+".html";
        try {
            String result=HttpClientUtil.doGet(url,3000,null,null);
            if(!StringUtils.isEmpty(result)&&result.length()>2){
                List<String> resultList= new ArrayList<>(Arrays.asList(result.substring(1,result.length()-1).split(",")));
                if(resultList.stream().filter(pro->pro.contains(county)).findAny().isPresent()){
                    return true;
                }
            }else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {

        }
        return false;
    }

    private String getCountyTemperature(String province, String city, String county){

        String url="http://www.weather.com.cn/data/sk/"+province+city+county+".html";
        try {
            String result=HttpClientUtil.doGet(url,30000,null,null);
            if(StringUtils.isEmpty(result)){
                throw new Exception("获取失败,请稍后再试");
            }
            Gson gson = new Gson();
            WeatherVo weatherVo =  gson.fromJson(result, WeatherVo.class);
            return new BigDecimal(weatherVo.getWeatherinfo().getTemp()).intValue()+"";
        } catch (Exception e) {
            e.printStackTrace();
        }finally {

        }
        return "";
    }

    public static void main(String [] args) throws Exception{
        ChinaWeather chinaWeather=new ChinaWeather();
        System.out.println(chinaWeather.getTemperature("10119","04","01"));
    }

}
