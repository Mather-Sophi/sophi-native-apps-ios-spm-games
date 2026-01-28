//
//  File.swift
//  app
//
//  Created by Siddhant Misra on 2025-11-18.
//

import Foundation
import Paywall

var _wd: WallDecision? = nil

struct ContentData: Identifiable{
    let id: String
    let headline: String
    let byline: String
    let plainText: String
    let imageUrl: String?
    var decision: WallDecision? = nil
    
    init(id: String, headline: String, byline: String, plainText: String, imageUrl: String?, decide: Bool = false) {
        self.id = id
        self.headline = headline
        self.byline = byline
        self.plainText = plainText
        self.imageUrl = imageUrl
        if(decide)
            {
                getDecision()
            }
    }
    
    mutating func getDecision() {
        let contentId = id
        Task {
            let result: WallDecision? = try await decider?.decide(contentId: contentId, assignedGroup: "group1", contentProperties: nil, userProperties: nil)
            if let result = result {
                _wd = result
            }
        }
        
        if let wd = _wd {
            self.decision = wd
        } else {
//            let outcome = Outcome(wallType: .nowall, wallVisibility: .never)
//            let experiment = Experiment(experimentId: "exp1", assignedGroup: "group2")
//            self.decision = WallDecision(id: "NotGenerated", createdAt: "NA", trace: "", context: "", inputs: "", outcome: outcome, searchParams: "", experiment: experiment)
            self.decision = nil
        }
    }
}
