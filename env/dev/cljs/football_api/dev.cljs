(ns ^:figwheel-no-load football-api.dev
  (:require
    [football-api.core :as core]
    [devtools.core :as devtools]))

(devtools/install!)

(enable-console-print!)

(core/init!)
