import { WallDecision } from "@mather-sophi/sophi-react-native-paywall-kit";
import { router, Stack, useLocalSearchParams } from "expo-router";
import { useEffect, useState } from "react";
import { ActivityIndicator, Image, Modal, Pressable, ScrollView, Text, View } from "react-native";
import { contentRepository } from "../content/content";
import { getPaywallDecision } from "../paywall/decider";
import { styles } from "../styles/article.styles";

export default function ArticleDetail() {
  const { id, assignedGroup } = useLocalSearchParams<{ id: string; assignedGroup?: string }>();
  const [decision, setDecision] = useState<WallDecision | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [showModal, setShowModal] = useState(false);

  const content = contentRepository.getContentById(id);

  useEffect(() => {
    if (id) {
      fetchDecision();
    }
  }, [id]);

  const fetchDecision = () => {
    setLoading(true);
    setError(null);
    try {
      const result = getPaywallDecision(id, assignedGroup);
      setDecision(result);
      setShowModal(true);
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Unknown error');
      setShowModal(true);
    } finally {
      setLoading(false);
    }
  };

  if (!content) {
    return (
      <View style={styles.container}>
        <Text style={styles.errorText}>Article not found</Text>
      </View>
    );
  }

  return (
    <View style={styles.container}>
      <Stack.Screen options={{ title: id }} />
      <ScrollView>
        {content.imageUrl && (
          <Image
            source={{ uri: content.imageUrl }}
            style={styles.heroImage}
            resizeMode="cover"
          />
        )}
        
        <View style={styles.contentContainer}>
          <Pressable style={styles.backButton} onPress={() => router.back()}>
            <Text style={styles.backButtonText}>← Back</Text>
          </Pressable>

          <Text style={styles.headline}>{content.headline}</Text>
          <Text style={styles.articleContent}>{content.plainText}</Text>
        </View>
      </ScrollView>

      <Modal
        visible={showModal}
        transparent={true}
        animationType="slide"
        onRequestClose={() => setShowModal(false)}
      >
        <View style={styles.modalOverlay}>
          <View style={styles.modalContent}>
            <View style={styles.modalHeader}>
              <Text style={styles.modalTitle}>Paywall Decision</Text>
              <Pressable onPress={() => setShowModal(false)}>
                <Text style={styles.closeButton}>✕</Text>
              </Pressable>
            </View>

            {loading ? (
              <View style={styles.modalBodyCenter}>
                <ActivityIndicator size="large" color="#4a90e2" />
                <Text style={styles.loadingText}>Loading decision...</Text>
              </View>
            ) : error ? (
              <View style={styles.modalBodyCenter}>
                <Text style={styles.errorText}>{error}</Text>
              </View>
            ) : decision ? (
              <ScrollView 
                style={styles.modalScrollView}
                contentContainerStyle={styles.modalBody}
              >
                <Text style={styles.sectionTitle}>Decision</Text>
                <View style={styles.decisionRow}>
                  <Text style={styles.decisionLabel}>ID:</Text>
                  <Text style={styles.decisionValue} selectable>{decision.id}</Text>
                </View>
                <View style={styles.decisionRow}>
                  <Text style={styles.decisionLabel}>Created At:</Text>
                  <Text style={styles.decisionValue}>{new Date(decision.createdAt).toLocaleString()}</Text>
                </View>
                <View style={styles.decisionRow}>
                  <Text style={styles.decisionLabel}>Trace:</Text>
                  <Text style={styles.decisionValue} selectable>{decision.trace}</Text>
                </View>
                <View style={styles.decisionRow}>
                  <Text style={styles.decisionLabel}>Context:</Text>
                  <Text style={styles.decisionValue} selectable>{decision.context}</Text>
                </View>
                <View style={styles.decisionRow}>
                  <Text style={styles.decisionLabel}>Search Params:</Text>
                  <Text style={styles.decisionValue} selectable>{decision.searchParams}</Text>
                </View>

                <Text style={styles.sectionTitle}>Outcome</Text>
                <View style={styles.decisionRow}>
                  <Text style={styles.decisionLabel}>Wall Type:</Text>
                  <Text style={styles.decisionValue}>{decision.outcome.wallType || 'None'}</Text>
                </View>
                <View style={styles.decisionRow}>
                  <Text style={styles.decisionLabel}>Wall Type Code:</Text>
                  <Text style={styles.decisionValue}>{decision.outcome.wallTypeCode !== null ? decision.outcome.wallTypeCode.toString() : 'N/A'}</Text>
                </View>
                <View style={styles.decisionRow}>
                  <Text style={styles.decisionLabel}>Wall Visibility:</Text>
                  <Text style={styles.decisionValue}>{decision.outcome.wallVisibility}</Text>
                </View>
                <View style={styles.decisionRow}>
                  <Text style={styles.decisionLabel}>Wall Visibility Code:</Text>
                  <Text style={styles.decisionValue}>{decision.outcome.wallVisibilityCode}</Text>
                </View>

                <Text style={styles.sectionTitle}>Experiment</Text>
                <View style={styles.decisionRow}>
                  <Text style={styles.decisionLabel}>Experiment ID:</Text>
                  <Text style={styles.decisionValue} selectable>{decision.experiment.experimentId}</Text>
                </View>
                <View style={styles.decisionRow}>
                  <Text style={styles.decisionLabel}>Assigned Group:</Text>
                  <Text style={styles.decisionValue}>{decision.experiment.assignedGroup}</Text>
                </View>
                {assignedGroup && (
                  <View style={styles.decisionRow}>
                    <Text style={styles.decisionLabel}>Requested Group:</Text>
                    <Text style={styles.decisionValue}>{assignedGroup}</Text>
                  </View>
                )}

                <Text style={styles.sectionTitle}>Inputs</Text>
                <View style={styles.decisionRow}>
                  <Text style={styles.decisionValue} selectable>{decision.inputs}</Text>
                </View>
              </ScrollView>
            ) : (
              <View style={styles.modalBodyCenter}>
                <Text style={styles.errorText}>No decision available</Text>
              </View>
            )}

            <Pressable 
              style={styles.closeModalButton}
              onPress={() => setShowModal(false)}
            >
              <Text style={styles.closeModalButtonText}>Close</Text>
            </Pressable>
          </View>
        </View>
      </Modal>
    </View>
  );
}
