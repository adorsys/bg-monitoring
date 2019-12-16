{{/* vim: set filetype=mustache: */}}
{{/*
Expand the name of the chart.
*/}}
{{- define "bg-monitoring.name" -}}
{{- default "bg-monitoring" .Values.nameOverride | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{/*
Create a default fully app name truncate at 63 chars because some Kubernetes name fields are limited to this (by the DNS naming spec).
*/}}
{{- define "bg-monitoring.fullname" -}}
{{- if .Values.fullnameOverride -}}
{{- .Values.fullnameOverride | trunc 63 | trimSuffix "-" -}}
{{- else -}}
{{- $name := default .Chart.Name .Values.nameOverride -}}
{{- if contains $name .Release.Name -}}
{{- printf .Release.Name | trunc 63 | trimSuffix "-" -}}
{{- else -}}
{{- printf "%s-%s" .Release.Name $name | trunc 63 | trimSuffix "-" -}}
{{- end -}}
{{- end -}}
{{- end -}}

{{/* Helm required labels */}}
{{- define "bg-monitoring.labels" -}}
app: "{{ template "bg-monitoring.fullname" . }}"
helm.sh/chart: "{{ .Chart.Name }}-{{ .Chart.Version }}"
app.kubernetes.io/managed-by: {{ .Release.Service | quote }}
app.kubernetes.io/instance: {{ .Release.Name | quote }}
app.kubernetes.io/name: "{{ template "bg-monitoring.name" . }}"
app.kubernetes.io/version: "{{ .Chart.AppVersion }}"
{{- end -}}

{{/* matchLabels */}}
{{- define "bg-monitoring.matchLabels" -}}
release: {{ .Release.Name }}
app: "{{ template "bg-monitoring.fullname" . }}"
{{- end -}}
