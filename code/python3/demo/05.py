def reward_function(params):
    # 更细致的中心线奖励机制
    marker_1 = 0.1 * track_width
    marker_2 = 0.25 * track_width
    marker_3 = 0.5 * track_width

    # 指数衰减的奖励机制
    if distance_from_center <= marker_1:
        reward = 1.0
    elif distance_from_center <= marker_2:
        reward = 0.75
    elif distance_from_center <= marker_3:
        reward = 0.5
    else:
        reward = 1e-3  # 严重惩罚

    # 速度控制
    if speed > AVG_MAX_SPEED:
        reward *= 0.8  # 轻微惩罚过快

    # 赛道进度奖励
    progress_reward = closest_waypoints[1] / 100.0
    reward += progress_reward

    # 防止剧烈转向
    steering = params['steering_angle']
    if abs(steering) > 15:  # 大于15度惩罚
        reward *= 0.7

    return float(reward)