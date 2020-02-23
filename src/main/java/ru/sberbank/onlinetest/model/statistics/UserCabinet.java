package ru.sberbank.onlinetest.model.statistics;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCabinet {
    //user correct answers percentage
    private Float successRate;

    //percentage of users who answered worse than current user
    private Float worseRate;

    //percentage of users who answered better than current user
    private Float betterRate;
}
