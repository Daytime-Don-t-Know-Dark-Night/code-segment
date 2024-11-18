def reward_function(params):
    '''
    Example of rewarding the agent to follow center line
    '''

    # Read input parameters
    track_width = params['track_width']
    distance_from_center = params['distance_from_center']
    speed = params['speed']
    closest_waypoints = params['closest_waypoints']

    # max speed
    AVG_MAX_SPEED = 2

    # straight line max speed
    STRAIGHT_LINE_MAX_SPEED = 2.5

    # Calculate 3 markers that are at varying distances away from the center line
    marker_1 = 0.1 * track_width
    marker_2 = 0.25 * track_width
    marker_3 = 0.5 * track_width

    # Give higher reward if the car is closer to center line and vice versa
    if distance_from_center <= marker_1:
        reward = 1.0 + speed
    elif distance_from_center <= marker_2:
        reward = 0.5 + speed * 0.5
    elif distance_from_center <= marker_3:
        reward = 0.1 + speed * 0.2
    else:
        reward = 1e-3  # likely crashed/ close to off track

    if closest_waypoints[1] <= 15 and speed >= STRAIGHT_LINE_MAX_SPEED:
        reward = reward + 0.5

    if closest_waypoints[1] >= 90 and closest_waypoints[1] <= 99 and speed >= 2.5:
        reward = reward + 0.5

    if speed < AVG_MAX_SPEED:
        reward = reward + 0.5
    else:
        reward = reward + 1.0

    return float(reward)




