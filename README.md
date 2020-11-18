# Planetary Rover Communications Module
### Final Project for CSC231 Computer Systems

#### Team Capsicum
Sabrina Contaldi <br>
Wesley Hershberger <br>
Vidit Khandelwal

- Used PEP9 Assembly and Java

### Status Formulae

|Field Name                                                                                                      |# of Bits     |Range  |
|----------------------------------------------------------------------------------------------------------------|--------------|-------|
|Latitude                                                                                                        |15            |encode(x) = (x + 90) * 100 decode(x) = (x / 100) - 90|
|Longitude                                                                                                       |16            |encode(x) = (x + 180) * 100 decode(x) = (x / 100) - 180|
|Elevation                                                                                                       |15            |encode(x) = (x + 200) * 2 decode(x) = (x / 2) - 200|
|Ambient Temp                                                                                                    |10            |encode(x) = (x + 200) * 2 decode(x) = (x / 2) - 200|
|Ambient Light                                                                                                   |16            |significand(x) = 14 least-significant bits exponent(x) = 2 greatest-significant bits  encode(x) =  for exponent in [0, 3]  if x < 2^14 * 2^exponent:  exponent = exponent  significand = round(x / 2^exponent)  return decode(x) = significand(x) * 2^exponent(x)|
|Wind Speed                                                                                                      |9             |encode(x) = x * 2 decode(x) = x / 2|
|Internal Temp                                                                                                   |9             |encode(x) = (x + 50) * 2 decode(x) = (x / 2) - 50|
|Charge level                                                                                                    |9             |encode(x) = (x*4) decode(x) = (x/4)|
|Subsystem Indicator                                                                                             |10            |No Encoding|
|Subsystem Status Code                                                                                           |6             |No Encoding|
|Alert Indicator                                                                                                 |1             |No Alert = 0 Alert = 1|
|Internal time                                                                                                   |48            |encode(x) = x decode(x) = x|
|Hours of operation                                                                                              |20            |encode(x) = x decode(x) = x|

### Status Message Format

|Byte boundary                                                                                                   |Length (bits) |Name   |
|----------------------------------------------------------------------------------------------------------------|--------------|-------|
|6                                                                                                               |48            |Internal Time|
|2                                                                                                               |16            |Ambient Light|
|2                                                                                                               |16            |Longitude|
|3                                                                                                               |15            |Latitude|
|                                                                                                                |9             |Wind Speed|
|3                                                                                                               |15            |Elevation|
|                                                                                                                |9             |Internal Temperature|
|2                                                                                                               |10            |Subsystem Indicator|
|                                                                                                                |6             |Subsystem Code|
|5                                                                                                               |20            |Hours of Operation|
|                                                                                                                |10            |Ambient Temperature|
|                                                                                                                |9             |Charge Level|
|                                                                                                                |1             |Alert Indicator|
|**Message Size**                                                                                                    |**Total Bytes**   |       |
|**23**                                                                                                              |**23**            |       |

### Command Formulae

|Field Name                                                                                                      |# of Bits     |Range  |
|----------------------------------------------------------------------------------------------------------------|--------------|-------|
|Mission Mode                                                                                                    |4             |Navigate = 0 Recharge = 1 Wait_Light_Above = 2 Wait_Light_Below = 3 Wait_AmbientTemp_Above = 4 Wait_AmbientTemp_Below = 5 Wait_InternalTemp_Above = 6 Wait_InternalTemp_Below = 7 Wait_WindSpeed_Above = 8 Wait_WindSpeed_Below = 9 Wait_Time = 10|
|Latitude                                                                                                        |15            |encode(x) = (x + 90) * 100 decode(x) = (x / 100) - 90|
|Longitude                                                                                                       |16            |encode(x) = (x + 180) * 100 decode(x) = (x / 100) - 180|
|Charge Level                                                                                                    |9             |encode(x) = (x*4) encode(x) = (x/4)|
|Wait Condition                                                                                                  |32            |Varies based on value in Mission Mode|
|Lighting Level                                                                                                  |16            |significand(x) = 14 least-significant bits exponent(x) = 2 greatest-significant bits  encode(x) =  for exponent in [0, 3]  if x < 2^14 * 2^exponent:  exponent = exponent  significand = round(x / 2^exponent)  return decode(x) = significand(x) * 2^exponent(x)|
|Ambient Temperature                                                                                             |10            |encode(x) = (x + 200) * 2 decode(x) = (x / 2) - 200|
|Internal Temperature                                                                                            |9             |encode(x) = (x+50)*2 decode(x) = (x/2)-50|
|Wind Speed                                                                                                      |9             |encode(x) = x*2 decode(x) = x/2|
|Time                                                                                                            |48            |encode(x) = x decode(x) = x|

### Command Message Layout

|Byte Boundary                                                                                                   |Length (bits) |Name   |
|----------------------------------------------------------------------------------------------------------------|--------------|-------|
|6                                                                                                               |48            |Time   |
|4                                                                                                               |32            |Wait Condition|
|2                                                                                                               |16            |Lighting Level|
|2                                                                                                               |16            |Longitude|
|3                                                                                                               |15            |Latitude|
|                                                                                                                |9             |Charge Level|
|4                                                                                                               |10            |Ambient Temperature|
|                                                                                                                |9             |Internal Temperature|
|                                                                                                                |9             |Wind Speed|
|                                                                                                                |4             |Mission Modes|
|**Message Size**                                                                                                    |**Total Bytes**   |       |
|**21**                                                                                                              |**21**            |       |

