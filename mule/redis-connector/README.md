![Deloitte Digital](doc/deloittedigital-logo-white.png)

# Redis Anypoint Connector

Jedis client lib (https://github.com/xetorthio/jedis).

Embedded-redis (Integration/Unit Tests) https://github.com/kstyrc/embedded-redis

# Mule supported versions
Mule 3.7.x, 3.8.x

# Installation 
For beta connectors you can download the source code and build it with devkit to find it available on your local repository. Then you can add it to Studio

`Commin soon:` For released connectors you can download them from the update site in Anypoint Studio.
Open Anypoint Studio, go to Help → Install New Software and select Anypoint Connectors Update Site where you’ll find all avaliable connectors.

#Usage
For information about usage our documentation at https://github.com/DeloitteDigital/mule-redis-connector.

# Reporting Issues

Issues for tracking issues with this connector. You can report new issues at this link https://github.com/DeloitteDigital/mule-redis-connector/issues.

#Execute Integration Tests:
    $ mvn clean package -Dtest=FunctionalTestSuite

#Install into local MVN Repo
    $ mvn clean install -Dtest=FunctionalTestSuite

## Change log

`1.0.0` - June 2016

* Published on Git

### Deloitte Digital South Africa
* [@mdebarros](https://github.com/mdebarros)


## Who is Deloitte Digital?

**Part Business. Part Creative. Part Technology. One hundred per cent digital.**

Pioneered in South Africa, Deloitte Digital is committed to helping clients unlock the business value of emerging technologies. We provide clients with a full suite of digital services, covering digital strategy, user experience, content, creative, engineering and implementation across mobile, web and social media channels.

[http://deloittedigital.co.za/](http://deloittedigital.co.za/)

## LICENSE (BSD-3-Clause)
Copyright (C) 2015, Deloitte Digital. All rights reserved.

mule-redis-connector can be downloaded from: https://github.com/DeloitteDigital/mule-redis-connector

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

* Redistributions of source code must retain the above copyright notice, this
list of conditions and the following disclaimer.

* Redistributions in binary form must reproduce the above copyright notice,
this list of conditions and the following disclaimer in the documentation
and/or other materials provided with the distribution.

* Neither the name of the copyright holder nor the names of its contributors 
may be used to endorse or promote products derived from this software without 
specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
