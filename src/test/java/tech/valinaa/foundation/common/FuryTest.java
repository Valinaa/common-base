package tech.valinaa.foundation.common;

import org.apache.fury.Fury;
import org.apache.fury.ThreadSafeFury;
import org.apache.fury.config.CompatibleMode;
import org.apache.fury.config.Language;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import tech.valinaa.foundation.common.utils.CompressUtils;
import tech.valinaa.foundation.common.utils.JsonUtils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author Valinaa
 * @Date 2024/11/25 16:08
 */
public class FuryTest {
    private static ThreadSafeFury fury;
    
    @BeforeAll
    public static void init() {
        fury = Fury.builder()
//                .requireClassRegistration(true)
                .withCompatibleMode(CompatibleMode.SCHEMA_CONSISTENT)
                .withLanguage(Language.JAVA)
                .buildThreadSafeFury();
    }
    
    @Test
    void testFury() {
        String res = "{\"corpID\":\"corpsztest\",\"uID\":\"2121866387\",\"approvalNumber\":\"提前审批测试卡1\"," +
                "\"description\":\"订单验证不通过: 包车开始城市,包车结束城市不匹配\",\"endorsementName\":null,\"productType\":\"CAR_CHARTER\",\"verificationResultList\":[{\"fieldName\":\"CarCharter.Currency\",\"approvalFieldValue\":\"CNY\",\"orderFieldValue\":\"CNY\",\"id\":1000,\"matched\":true},{\"fieldName\":\"CarCharter.Price\",\"approvalFieldValue\":\"1000\",\"orderFieldValue\":\"1000\",\"id\":1000,\"matched\":true},{\"fieldName\":\"CarCharter.PassportName\",\"approvalFieldValue\":\"张三,王五\",\"orderFieldValue\":\"张三,王五\",\"id\":1000,\"matched\":true},{\"fieldName\":\"CarCharter.DepartCityID\",\"approvalFieldValue\":\"北京(1),非法城市ID(32324324234234)\",\"orderFieldValue\":\"上海(2)\",\"id\":100,\"matched\":false},{\"fieldName\":\"CarCharter.DestCityID\",\"approvalFieldValue\":\"北京(1),上海(2)\",\"orderFieldValue\":\"天津(3)\",\"id\":100,\"matched\":false},{\"fieldName\":\"CarCharter.VehicleGroup\",\"approvalFieldValue\":\"经济型,舒适型,商务型,豪华型,巴士型\",\"orderFieldValue\":\"豪华型\",\"id\":1000,\"matched\":true},{\"fieldName\":\"CarCharter.CharterDate\",\"approvalFieldValue\":\"2017-09-21至2019-10-13\",\"orderFieldValue\":\"2018-10-02\",\"id\":1000,\"matched\":true},{\"fieldName\":\"CarCharter.CharterTime\",\"approvalFieldValue\":\"08:00~18:00\",\"orderFieldValue\":\"09:00\",\"id\":1000,\"matched\":true},{\"fieldName\":\"CarCharter.UseDays\",\"approvalFieldValue\":\"10\",\"orderFieldValue\":\"3\",\"id\":1000,\"matched\":true},{\"fieldName\":\"CarCharter.SeatsNum\",\"approvalFieldValue\":\"5\",\"orderFieldValue\":\"5\",\"id\":1000,\"matched\":true},{\"fieldName\":\"CarCharter.ComboID\",\"approvalFieldValue\":\"10小时包车-含300公里/天\",\"orderFieldValue\":\"10小时包车-含300公里/天\",\"id\":1000,\"matched\":true}],\"canSubmitByOverApply\":null,\"needAuthorize\":null,\"pass\":false,\"rank\":\"\",\"rankDetail\":null,\"remark\":\"Remark字段，传500长度内任何字符\",\"travelStandControlModel\":null,\"transactionID\":\"f4b593c0-8d33-4d2f-98b6-93916a3212ee\",\"controlBudget\":false,\"controlEffectivenessAmount\":false,\"controlBudgetID\":null,\"controlEffectivenessAmountID\":null,\"budgetAmount\":null,\"budgetCurrency\":null,\"ResponseStatus\":{\"Ack\":\"Success\",\"Build\":null,\"Errors\":[],\"Extension\":[],\"Timestamp\":1672906132374,\"Version\":null},\"status\":{\"success\":false,\"errorCode\":10303411,\"errorMessage\":\"订单验证不通过，审批单管控字段不匹配\"},\"preApprovalConfigInfo\":{\"canSubPreApproFail\":\"F\",\"isAuthPreApproPass\":\"F\"}}";
        var aov = JsonUtils.parseObject(res, Object.class);
        var furyStr = Base64.getEncoder().encodeToString(CompressUtils.zstdCompress(fury.serialize(aov)));
        var str = Base64.getEncoder().encodeToString(CompressUtils.zstdCompress(res.getBytes(StandardCharsets.UTF_8)));
        System.out.println("json length: " + str.getBytes(StandardCharsets.UTF_8).length + " fury length: " + furyStr.getBytes(StandardCharsets.UTF_8).length);
    }
}
