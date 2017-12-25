(ns football-api.core
    (:require [reagent.core :as reagent :refer [atom]]
              [secretary.core :as secretary :include-macros true]
              [cljs.core.async :refer [<!]]
              [cljs-http.client :as http]
              [accountant.core :as accountant]
              [football-api.pages.home_page :as home_page]
              [football-api.pages.about_page :as about_page]
              [football-api.models.test_model :as test_model]
              [football-api.models.artists-model :as artists-model]
	)
	(:require-macros [cljs.core.async.macros :refer [go]]))

(devtools.core/set-pref! :dont-detect-custom-formatters true)
(def log (.-log js/console))

(defn handler2 [response]
	(log (str response)))

; @SOURCE: https://github.com/r0man/cljs-http
(def artists_top (atom (array-map)))
(defn test_ajax_call []
	(go (let [response (<! (http/get "/divide?x=34&y=44"))]
		    (prn  (:body response))
		    (artists-model/add_artist (get-in (:body response) [:album]))
		    )))

;; -------------------------
;; Routes

(def page (atom #'home_page/render))

(defn current-page []
	(log "render layout")
    [:div [:span "@test_atom: "] @test_model/test_atom [@page] [:button {:on-click test_ajax_call} "test ajax call to (/divide?x=34&y=44)"]])

(secretary/defroute "/" []
  (reset! page #'home_page/render))

(secretary/defroute "/about" []
  (reset! page #'about_page/render))

(secretary/defroute "/user/:userId" [userId]
          (js/console.log (str userId)))

;; -------------------------
;; Initialize app

(defn mount-root []
  (reagent/render [current-page] (.getElementById js/document "app")))

(defn init! []
  (accountant/configure-navigation!
    {:nav-handler
     (fn [path]
       (secretary/dispatch! path))
     :path-exists?
     (fn [path]
       (secretary/locate-route path))})
  (accountant/dispatch-current!)
  (mount-root))
