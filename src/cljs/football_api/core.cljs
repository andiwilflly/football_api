(ns football-api.core
    (:require [reagent.core :as reagent :refer [atom]]
              [secretary.core :as secretary :include-macros true]
              [cljs.core.async :refer [<!]]
              [cljs-http.client :as http]
              [accountant.core :as accountant]
              [football-api.pages.home :as home]
              [football-api.pages.about :as about]
	)
	(:require-macros [cljs.core.async.macros :refer [go]]))


(def log (.-log js/console))



(defn handler2 [response]
	(log (str response)))

; @SOURCE: https://github.com/r0man/cljs-http
(defn test_ajax_call []
	(go (let [response (<! (http/get "/artists/top"))]
		    (prn (:body response)))))

;; -------------------------
;; Routes

(def page (atom #'home/home-page))

(defn current-page []
  [:div [:span "Here is app..."] [@page] [:button {:on-click test_ajax_call} "test ajax call to (/artists/top)"]])

(secretary/defroute "/" []
  (reset! page #'home/home-page))

(secretary/defroute "/about" []
  (reset! page #'about/about-page))

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
