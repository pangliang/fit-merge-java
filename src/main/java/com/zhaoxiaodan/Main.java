package com.zhaoxiaodan;

import com.garmin.fit.*;
import com.garmin.fit.plugins.HrToRecordMesgBroadcastPlugin;
import org.apache.commons.collections4.CollectionUtils;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        FitMessages fitMessagesSource;
        Map<Long, RecordMesg> timestampMap = new HashMap<>();
        try (FileInputStream inputStream = new FileInputStream("G:/Downloads/2023-12-29-15-54-37.fit")) {
            FitDecoder fitDecoder = new FitDecoder();
            fitMessagesSource = fitDecoder.decode(inputStream);
            for(RecordMesg record : fitMessagesSource.getRecordMesgs()) {
                timestampMap.put(record.getTimestamp().getTimestamp(), record);
            }
        }catch (Exception e) {
            e.printStackTrace();
            return;
        }

        FitMessages fitMessagesTarget;
        try (FileInputStream inputStream = new FileInputStream("G:/Downloads/13270451087_ACTIVITY.fit")) {
            FitDecoder fitDecoder = new FitDecoder();
            fitMessagesTarget = fitDecoder.decode(inputStream, new HrToRecordMesgBroadcastPlugin());

            // 每个记录点数据中, 替换坐标, 速度, 海拔
            for(RecordMesg record : fitMessagesTarget.getRecordMesgs()) {
                if(timestampMap.containsKey(record.getTimestamp().getTimestamp())) {
                    RecordMesg recordGps = timestampMap.get(record.getTimestamp().getTimestamp());
                    record.setPositionLat(recordGps.getPositionLat());
                    record.setPositionLong(recordGps.getPositionLong());
//                    record.setSpeed(recordGps.getSpeed());
//                    record.setAltitude(recordGps.getAltitude());
//                    record.setEnhancedAltitude(recordGps.getEnhancedAltitude());
//                    record.setEnhancedSpeed(recordGps.getEnhancedSpeed());
                }
            }

//            // 平均值
//            if(CollectionUtils.isNotEmpty(fitMessagesTarget.getSessionMesgs()) && CollectionUtils.isNotEmpty(fitMessagesSource.getSessionMesgs())) {
//                SessionMesg sessionSource = fitMessagesSource.getSessionMesgs().get(0);
//                SessionMesg sessionTarget = fitMessagesTarget.getSessionMesgs().get(0);
//                sessionTarget.setAvgAltitude(sessionSource.getAvgAltitude());
//                sessionTarget.setAvgSpeed(sessionSource.getAvgSpeed());
//                sessionTarget.setMaxAltitude(sessionSource.getMaxAltitude());
//                sessionTarget.setMaxSpeed(sessionSource.getMaxSpeed());
//                sessionTarget.setEnhancedAvgSpeed(sessionSource.getEnhancedAvgSpeed());
//                sessionTarget.setEnhancedMaxSpeed(sessionSource.getEnhancedMaxSpeed());
//                sessionTarget.setTotalMovingTime(sessionSource.getTotalMovingTime());
//                sessionTarget.setTotalDistance(sessionSource.getTotalDistance());
//
//                sessionTarget.setNumLaps(0);
//            }

            save(fitMessagesTarget, "G:/Downloads/ExampleActivity.fit");;
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void save(FitMessages fitMessages, String file) {
        FileEncoder encode = new FileEncoder(new java.io.File(file), Fit.ProtocolVersion.V2_0);

        encode.write(fitMessages.getFileIdMesgs());
        encode.write(fitMessages.getFileCreatorMesgs());
        encode.write(fitMessages.getTimestampCorrelationMesgs());
        encode.write(fitMessages.getSoftwareMesgs());
        encode.write(fitMessages.getSlaveDeviceMesgs());
        encode.write(fitMessages.getCapabilitiesMesgs());
        encode.write(fitMessages.getFileCapabilitiesMesgs());
        encode.write(fitMessages.getMesgCapabilitiesMesgs());
        encode.write(fitMessages.getFieldCapabilitiesMesgs());
        encode.write(fitMessages.getDeviceSettingsMesgs());
        encode.write(fitMessages.getUserProfileMesgs());
        encode.write(fitMessages.getHrmProfileMesgs());
        encode.write(fitMessages.getSdmProfileMesgs());
        encode.write(fitMessages.getBikeProfileMesgs());
        encode.write(fitMessages.getConnectivityMesgs());
        encode.write(fitMessages.getWatchfaceSettingsMesgs());
        encode.write(fitMessages.getOhrSettingsMesgs());
        encode.write(fitMessages.getTimeInZoneMesgs());
        encode.write(fitMessages.getZonesTargetMesgs());
        encode.write(fitMessages.getSportMesgs());
        encode.write(fitMessages.getHrZoneMesgs());
        encode.write(fitMessages.getSpeedZoneMesgs());
        encode.write(fitMessages.getCadenceZoneMesgs());
        encode.write(fitMessages.getPowerZoneMesgs());
        encode.write(fitMessages.getMetZoneMesgs());
        encode.write(fitMessages.getDiveSettingsMesgs());
        encode.write(fitMessages.getDiveAlarmMesgs());
        encode.write(fitMessages.getDiveApneaAlarmMesgs());
        encode.write(fitMessages.getDiveGasMesgs());
        encode.write(fitMessages.getGoalMesgs());
        encode.write(fitMessages.getActivityMesgs());
        encode.write(fitMessages.getSessionMesgs());

        encode.write(fitMessages.getLapMesgs());
//        encode.write(new ArrayList<LapMesg>());

        encode.write(fitMessages.getLengthMesgs());
        encode.write(fitMessages.getRecordMesgs());
        encode.write(fitMessages.getEventMesgs());
        encode.write(fitMessages.getDeviceInfoMesgs());
        encode.write(fitMessages.getDeviceAuxBatteryInfoMesgs());
        encode.write(fitMessages.getTrainingFileMesgs());
        encode.write(fitMessages.getWeatherConditionsMesgs());
        encode.write(fitMessages.getWeatherAlertMesgs());
        encode.write(fitMessages.getGpsMetadataMesgs());
        encode.write(fitMessages.getCameraEventMesgs());
        encode.write(fitMessages.getGyroscopeDataMesgs());
        encode.write(fitMessages.getAccelerometerDataMesgs());
        encode.write(fitMessages.getMagnetometerDataMesgs());
        encode.write(fitMessages.getBarometerDataMesgs());
        encode.write(fitMessages.getThreeDSensorCalibrationMesgs());
        encode.write(fitMessages.getOneDSensorCalibrationMesgs());
        encode.write(fitMessages.getVideoFrameMesgs());
        encode.write(fitMessages.getObdiiDataMesgs());
        encode.write(fitMessages.getNmeaSentenceMesgs());
        encode.write(fitMessages.getAviationAttitudeMesgs());
        encode.write(fitMessages.getVideoMesgs());
        encode.write(fitMessages.getVideoTitleMesgs());
        encode.write(fitMessages.getVideoDescriptionMesgs());
        encode.write(fitMessages.getVideoClipMesgs());
        encode.write(fitMessages.getSetMesgs());
        encode.write(fitMessages.getJumpMesgs());
        encode.write(fitMessages.getSplitMesgs());
        encode.write(fitMessages.getSplitSummaryMesgs());
        encode.write(fitMessages.getClimbProMesgs());
        encode.write(fitMessages.getFieldDescriptionMesgs());
        encode.write(fitMessages.getDeveloperDataIdMesgs());
        encode.write(fitMessages.getCourseMesgs());
        encode.write(fitMessages.getCoursePointMesgs());
        encode.write(fitMessages.getSegmentIdMesgs());
        encode.write(fitMessages.getSegmentLeaderboardEntryMesgs());
        encode.write(fitMessages.getSegmentPointMesgs());
        encode.write(fitMessages.getSegmentLapMesgs());
        encode.write(fitMessages.getSegmentFileMesgs());
        encode.write(fitMessages.getWorkoutMesgs());
        encode.write(fitMessages.getWorkoutSessionMesgs());
        encode.write(fitMessages.getWorkoutStepMesgs());
        encode.write(fitMessages.getExerciseTitleMesgs());
        encode.write(fitMessages.getScheduleMesgs());
        encode.write(fitMessages.getTotalsMesgs());
        encode.write(fitMessages.getWeightScaleMesgs());
        encode.write(fitMessages.getBloodPressureMesgs());
        encode.write(fitMessages.getMonitoringInfoMesgs());
        encode.write(fitMessages.getMonitoringMesgs());
        encode.write(fitMessages.getMonitoringHrDataMesgs());
        encode.write(fitMessages.getSpo2DataMesgs());
        encode.write(fitMessages.getHrMesgs());
        encode.write(fitMessages.getStressLevelMesgs());
        encode.write(fitMessages.getMaxMetDataMesgs());
        encode.write(fitMessages.getMemoGlobMesgs());
        encode.write(fitMessages.getSleepLevelMesgs());
        encode.write(fitMessages.getAntChannelIdMesgs());
        encode.write(fitMessages.getAntRxMesgs());
        encode.write(fitMessages.getAntTxMesgs());
        encode.write(fitMessages.getExdScreenConfigurationMesgs());
        encode.write(fitMessages.getExdDataFieldConfigurationMesgs());
        encode.write(fitMessages.getExdDataConceptConfigurationMesgs());
        encode.write(fitMessages.getDiveSummaryMesgs());
        encode.write(fitMessages.getHrvMesgs());
        encode.write(fitMessages.getBeatIntervalsMesgs());
        encode.write(fitMessages.getHrvStatusSummaryMesgs());
        encode.write(fitMessages.getHrvValueMesgs());
        encode.write(fitMessages.getRespirationRateMesgs());
        encode.write(fitMessages.getTankUpdateMesgs());
        encode.write(fitMessages.getTankSummaryMesgs());
        encode.write(fitMessages.getSleepAssessmentMesgs());
        encode.write(fitMessages.getPadMesgs());

        encode.close();
    }
}