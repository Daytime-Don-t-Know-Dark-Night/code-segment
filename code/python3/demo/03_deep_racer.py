def reward_function(params):
    '''
    Example of rewarding the agent to stay inside the two borders of the track
    '''

    # Read input parameters
    all_wheels_on_track = params['all_wheels_on_track']
    waypoints = params['waypoints']
    speed = params['speed']
    closest_waypoints = params['closest_waypoints']

    # 绿色代表高速, 蓝色代表中速, 剩下的是低速
    # 低速 1.2   中速 2.4   高速 3.6
    high = [[0, 13], [41, 53]]
    mid = [[53, 80], [90, 101]]
    inHigh = False
    inMid = False

    if closest_waypoints[0] > high[0][0] and closest_waypoints[0] < high[0][1]:
        inHigh = True
    elif closest_waypoints[0] > high[1][0] and closest_waypoints[0] < high[1][1]:
        inHigh = True
    elif closest_waypoints[0] > mid[0][0] and closest_waypoints[0] < mid[0][1]:
        inMid = True
    elif closest_waypoints[0] > mid[1][0] and closest_waypoints[0] < mid[1][1]:
        inMid = True

    if all_wheels_on_track and speed >= 3.0 and inHigh:
        reward = 1.0
    elif all_wheels_on_track and speed >= 2.0 and speed < 2.5 and inMid:
        reward = 1.0
    elif all_wheels_on_track and speed >= 1.0 and speed <= 1.2 and not inHigh and not inMid:
        reward = 1.0
    else:
        reward = 1e-3

    return float(reward)



def main():
    reward_function()