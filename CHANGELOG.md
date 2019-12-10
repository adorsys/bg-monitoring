# Change Log
All notable changes to this project will be documented in this file.
This change log follows the conventions of [keepachangelog.com](http://keepachangelog.com/).

## [Unreleased]
## [v0.1.0-rc2] - 2019-12-10
### Fixed
- [#11](https://github.com/adorsys/bg-monitoring/issues/11)
Client with monitoring disabled tries to establish connection to rabbitmq.
Failing `RabbitHealthIndicator` causes k8s deployment failure
because it uses `/actuator/health` as readiness probe.
- [#10](https://github.com/adorsys/bg-monitoring/issues/10)
`errorMessage` is null if an exception is caught by `@ExceptionHandler`

## [v0.1.0-rc1] - 2019-12-05
### Added
- Openshift helm chart with rabbitmq, influxdb, grafana and spring boot server app services.
- `bg-monitoring-api` maven module for `MonitoringMessage` model shared between client and server.
- Monitoring server app reading messages from RabbitMQ and storing them into influxdb.
- RabbitTemplate based monitoring client with autoconfigured spring-webmvc `HandlerInterceptor`.

[Unreleased]: https://github.com/adorsys/bg-monitoring/compare/v0.1.0-rc2...HEAD
[v0.1.0-rc2]: https://github.com/adorsys/bg-monitoring/releases/tag/v0.1.0-rc2
[v0.1.0-rc1]: https://github.com/adorsys/bg-monitoring/releases/tag/v0.1.0-rc1
